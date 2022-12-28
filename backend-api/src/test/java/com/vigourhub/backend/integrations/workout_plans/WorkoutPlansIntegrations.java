package com.vigourhub.backend.integrations.workout_plans;

import com.vigourhub.backend.integrations.test_env.AuthServerMock;
import com.vigourhub.backend.integrations.test_env.TestEnvironment;
import com.vigourhub.backend.VigourhubBackendApiApplication;
import com.vigourhub.backend.domain.adapters.WorkoutPlansRepositoryAdapter;
import com.vigourhub.backend.domain.adapters.WorkoutsRepositoryAdapter;
import com.vigourhub.backend.domain.entity.enums.WorkoutExecutionType;
import com.vigourhub.backend.domain.entity.workout_plans.WorkoutPlan;
import com.vigourhub.backend.domain.entity.workout_plans.WorkoutRoutine;
import com.vigourhub.backend.domain.entity.workouts.Workout;
import com.vigourhub.backend.dto.IdResponseDto;
import com.vigourhub.backend.dto.accounts.AccountRequestDTO;
import com.vigourhub.backend.dto.accounts.AccountResponseDTO;
import com.vigourhub.backend.dto.accounts.UserRequestDTO;
import com.vigourhub.backend.dto.workout_plans.*;
import com.vigourhub.backend.security.keycloak.KeycloakContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = VigourhubBackendApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient(registerRestTemplate = true)
public class WorkoutPlansIntegrations {

    @LocalServerPort
    private int port;
    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    WorkoutPlansRepositoryAdapter workoutPlansRepositoryAdapter;

    @Autowired
    WorkoutsRepositoryAdapter workoutsRepositoryAdapter;

    @Mock
    KeycloakContext keycloakContext;

    @BeforeAll
    public static void setup() {
        TestEnvironment.setupEnv();
        AuthServerMock.setup();
    }

    @Test
    public void testOK() {
        assertTrue("nenad".equals("nenad"));
    }

    @Test
    public void createWorkoutPlan_OK() throws Exception {

        var createdAccount = createAccount();
        var principal = createdAccount.getOwner();
        var accountID = createdAccount.getId();

        String accessToken = new AuthServerMock().generateToken(principal.getUsername());

        var planRequest = new WorkoutPlanRequestDTO();
        planRequest.setName("test-workout-plan");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<WorkoutPlanRequestDTO> req = new HttpEntity<>(planRequest, headers);

        var resp = restTemplate.postForEntity(
                getServerUrl("/api/v1/workout_plans"),
                req,
                WorkoutPlanResponseDTO.class);

        var createdPlanID = UUID.fromString(resp.getBody().getId());

        Optional<WorkoutPlan> optionalPlan = workoutPlansRepositoryAdapter.getPlanById(createdPlanID);

        assertFalse(optionalPlan.isEmpty());
        var plan = optionalPlan.get();
        var workoutPlanID = plan.getId();

        assertEquals(plan.getCreator().getId().toString(), principal.getId());

        var routineName = "my-routine-1";
        var routineUrl = getServerUrl(String.format("/api/v1/workout_plans/%s/routines", plan.getId().toString()));

        var routineRequest = new RoutineRequestDTO();
        routineRequest.setName(routineName);
        HttpEntity<RoutineRequestDTO> req1 = new HttpEntity<>(routineRequest, headers);

        var resp1 = restTemplate.postForEntity(routineUrl, req1, IdResponseDto.class);

        assertEquals(resp1.getStatusCode(), HttpStatus.OK);
        var routineID = UUID.fromString((resp1.getBody().getId()));

        Optional<WorkoutRoutine> optionalRoutine = workoutPlansRepositoryAdapter.getRoutineById(routineID);

        assertFalse(optionalRoutine.isEmpty());
        assertEquals(optionalRoutine.get().getName(), routineName);

        Page<Workout> workouts = workoutsRepositoryAdapter.getPagedWorkoutsForAccount(accountID, PageRequest.of(0, 15));
        var workoutList = workouts.getContent();

        assertTrue(workouts.getTotalElements() > 0);
        var counter = 0;
        for (var i = 0; i < workoutList.size(); i++) {

            var currentWorkout = workoutList.get(i);
            var routineWorkoutRequest = new RoutineWorkoutRequestDTO();
            routineWorkoutRequest.setWorkoutId(currentWorkout.getId().toString());
            routineWorkoutRequest.setExecutionType(WorkoutExecutionType.FixedRepetitions);

            routineWorkoutRequest.setWorkingSets(List.of(
                    generateSet(10.5F, 10, 1),
                    generateSet(215F, 15, 3),
                    generateSet(43.5F, 21, 3),
                    generateSet(102.5F, 11, 1)
            ));

            HttpEntity<RoutineWorkoutRequestDTO> req2 = new HttpEntity<>(routineWorkoutRequest, headers);
            var routineWorkoutURL = getServerUrl(String.format("/api/v1/workout_plans/%s/routines/%s/workouts",workoutPlanID.toString(), routineID));

            var resp2 = restTemplate.postForEntity(routineWorkoutURL, req2, RoutineWorkoutResponseDTO.class);

            assertEquals(resp2.getStatusCode(), HttpStatus.OK);
            counter++;
        }

        var planURL = getServerUrl(String.format("/api/v1/workout_plans/%s", workoutPlanID.toString()));
        HttpEntity<WorkoutPlanResponseDTO> req3 = new HttpEntity<>(null, headers);
        var resp3 = restTemplate.exchange(planURL,HttpMethod.GET, req3, WorkoutPlanResponseDTO.class);

        assertEquals(resp3.getStatusCode(), HttpStatus.OK);

        var workoutPlanDTO = resp3.getBody();

        assertEquals(workoutPlanDTO.getName(), planRequest.getName());

        var routinesDTO = workoutPlanDTO.getRoutines();
        assertEquals(routinesDTO.size(), 1);

        var routineWorkoutsDTO = routinesDTO.get(0).getWorkouts();

        assertEquals(routineWorkoutsDTO.size(), counter);

        for (RoutineWorkoutResponseDTO current : routineWorkoutsDTO) {
            assertEquals(current.getWorkingSets().size(), 4);
        }

    }

    private AccountResponseDTO createAccount() throws Exception {

        String username = String.format("user-%s@email.com", UUID.randomUUID());
        String accountName = String.format("account-%s", UUID.randomUUID());
        String password = String.format("%s123$", UUID.randomUUID());

        AccountRequestDTO requestDTO = new AccountRequestDTO();
        requestDTO.setName(accountName);
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setFirstName("Vigourhub");
        userRequestDTO.setLastName("Administrator");
        userRequestDTO.setUsername(username);
        userRequestDTO.setPassword(password);

        requestDTO.setUser(userRequestDTO);

        Mockito.when(keycloakContext.createKeycloakUser(Mockito.any())).thenReturn(UUID.randomUUID());

        ResponseEntity<AccountResponseDTO> response = restTemplate
                .postForEntity(getServerUrl("/api/v1/accounts"), requestDTO, AccountResponseDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        return response.getBody();
    }

    private WorkingSetDTO generateSet(float load, int repetitions, int count) {
        WorkingSetDTO set = new WorkingSetDTO();
        set.setSetCount(count);
        set.setRepetitions(repetitions);
        set.setLoad(load);
        return set;
    }

    private void cleanup() {

    }

//    private UUID createWorkoutPlan() {
//
//    }
//
//    private UUID createWorkoutRoutine() {
//
//    }
//
//    private UUID createMetadataWorkout(UUID workoutId) {
//
//    }
    private String getServerUrl(String endpoint) {
        return String.format("http://localhost:%s%s", port,endpoint);
    }

}
