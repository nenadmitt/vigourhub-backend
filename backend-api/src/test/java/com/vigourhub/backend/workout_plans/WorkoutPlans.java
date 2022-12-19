package com.vigourhub.backend.workout_plans;

import com.vigourhub.backend.AuthServerMock;
import com.vigourhub.backend.TestEnvironment;
import com.vigourhub.backend.VigourhubBackendApiApplication;
import com.vigourhub.backend.domain.adapters.WorkoutPlansRepositoryAdapter;
import com.vigourhub.backend.domain.entity.workout_plans.WorkoutPlan;
import com.vigourhub.backend.domain.entity.workout_plans.WorkoutRoutine;
import com.vigourhub.backend.domain.repository.AccountRepository;
import com.vigourhub.backend.domain.repository.UserRepository;
import com.vigourhub.backend.dto.IdResponseDto;
import com.vigourhub.backend.dto.accounts.AccountRequestDTO;
import com.vigourhub.backend.dto.accounts.AccountResponseDTO;
import com.vigourhub.backend.dto.accounts.UserRequestDTO;
import com.vigourhub.backend.dto.workout_plans.WorkoutPlanRequestDTO;
import com.vigourhub.backend.dto.workout_plans.WorkoutPlanResponseDTO;
import com.vigourhub.backend.security.keycloak.KeycloakContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

import static com.vigourhub.backend.TestEnvironment.setupEnv;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = VigourhubBackendApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient(registerRestTemplate = true)
public class WorkoutPlans {

    @LocalServerPort
    private int port;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AccountRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WorkoutPlansRepositoryAdapter workoutPlansRepositoryAdapter;

    @Mock
    KeycloakContext keycloakContext;

    @BeforeAll
    public static void setup() {
        TestEnvironment.setupEnv();
        AuthServerMock.generateSigningKeys();;
    }

    @Test
    public void createWorkoutPlan_OK() throws Exception {

        var createdAccount = createAccount();
        var principal = createdAccount.getOwner();

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

        assertEquals(plan.getCreator().getId().toString(), principal.getId());

        var routineName = "my-routine-1";
        var routineUrl = getServerUrl(String.format("/workout_plans/%s/routines?name=%s", plan.getId().toString(), routineName));

        HttpEntity<Void> req1 = new HttpEntity<>(null, headers);

        var resp1 = restTemplate.postForEntity(routineUrl, req1, IdResponseDto.class);

        assertEquals(resp1.getStatusCode(), HttpStatus.OK);
        var routineID = UUID.fromString((resp1.getBody().getId()));

        Optional<WorkoutRoutine> optionalRoutine = workoutPlansRepositoryAdapter.getRoutineById(routineID);

        assertFalse(optionalRoutine.isEmpty());

        assertEquals(optionalRoutine.get().getName(), routineName);

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

        String token = new AuthServerMock().generateToken(userRequestDTO.getUsername());

        return response.getBody();
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
