package com.vigourhub.backend.service.impl;

import com.vigourhub.backend.domain.adapters.WorkoutsRepositoryAdapter;
import com.vigourhub.backend.domain.models.account.Account;
import com.vigourhub.backend.domain.models.account.User;
import com.vigourhub.backend.domain.models.workouts.*;
import com.vigourhub.backend.dto.IdResponseDto;
import com.vigourhub.backend.dto.workout_plans.FullWorkoutPlanDto;
import com.vigourhub.backend.dto.workout_plans.WorkoutPlanRequest;
import com.vigourhub.backend.infrastructure.exceptions.ForbiddenException;
import com.vigourhub.backend.infrastructure.exceptions.NotFoundException;
import com.vigourhub.backend.infrastructure.security.SecurityUtils;
import com.vigourhub.backend.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class WorkoutPlanServiceImpl implements WorkoutPlanService {

    private WorkoutsRepositoryAdapter workoutRepositoryAdapter;

    @Autowired
    public WorkoutPlanServiceImpl(WorkoutsRepositoryAdapter workoutRepositoryAdapter) {
        this.workoutRepositoryAdapter = workoutRepositoryAdapter;
    }

    @Override
    public IdResponseDto createWorkoutPlan(WorkoutPlanRequest request) {

        var principal = SecurityUtils.getCurrentPrincipal();

        var creator = new User();
        creator.setId(UUID.fromString(principal.getUserId()));

        var account = new Account();
        account.setId(UUID.fromString(principal.getAccountId()));

        WorkoutPlan plan = new WorkoutPlan();
        plan.setId(UUID.randomUUID());
        plan.setCreator(creator);
        plan.setName(request.getName());
        plan.setCompleted(false);

        workoutRepositoryAdapter.insert(plan);
        return new IdResponseDto(plan.getId().toString());
    }

    @Override
    public IdResponseDto createWorkoutRoutine(UUID workoutPlanId, String name) throws Exception {

        var optionalPlan = this.getPlan(workoutPlanId);

        if (optionalPlan.isEmpty()) {
            throw new NotFoundException("Plan is not found");
        }

        var principal = SecurityUtils.getCurrentPrincipal();
        var plan = optionalPlan.get();

        if (!principal.getUserId().equals(plan.getCreator().getId().toString())) {
            throw new ForbiddenException("No permission for this action");
        }

        WorkoutRoutine routine = new WorkoutRoutine();
        routine.setName(name);
        routine.setWorkoutPlan(plan);
        routine.setId(UUID.randomUUID());

        this.workoutRepositoryAdapter.insert(routine);


        return new IdResponseDto(routine.getId().toString());
    }

    @Override
    public IdResponseDto addRoutineWorkout(UUID workoutPlanId, UUID routineId) throws Exception {

        var optionalPlan = getPlan(workoutPlanId);
        if (optionalPlan.isEmpty()) {
            throw new NotFoundException("Plan not found");
        }

        var plan = optionalPlan.get();
        var userCreatorId = plan.getCreator().getId().toString();
        var principal = SecurityUtils.getCurrentPrincipal();

        if (!principal.getUserId().equals(userCreatorId)) {
            throw new ForbiddenException("No permission for this action");
        }

        Optional<WorkoutRoutine> optionalRoutine = this.workoutRepositoryAdapter.getRoutineById(routineId);

        if (optionalRoutine.isEmpty()) {
            throw new NotFoundException("Routine not found");
        }


        var routine = new WorkoutRoutine();
        routine.setId(routineId);


        var deadliftId = UUID.fromString("ee797847-3d99-44e6-b36d-db569e9e2715");
        System.out.println(deadliftId.toString());
        Optional<Workout> optionalWorkout = this.workoutRepositoryAdapter.getWorkoutById(deadliftId);

        if (optionalWorkout.isEmpty()) {
            throw new NotFoundException("Workout not found");
        }

        WorkoutMetadata metadata = new WorkoutMetadata();
//        metadata.setLoad(1.1F);
//        metadata.setRepetitions(5);
//        metadata.setSet(5);

        RoutineWorkout workout = new RoutineWorkout();
        workout.setId(UUID.randomUUID());
        workout.setRoutine(routine);
        workout.setWorkout(optionalWorkout.get());


        workout.setMetadata(metadata);

        this.workoutRepositoryAdapter.insert(workout);
        return null;
    }

    @Override
    public FullWorkoutPlanDto getFullWorkoutPlan(UUID workoutPlanId) throws NotFoundException {

        Optional<WorkoutPlan> optionalPlan = workoutRepositoryAdapter.getPlanById(workoutPlanId);

        if (optionalPlan.isEmpty()) {
            throw new NotFoundException("Plan not found");
        }

        return FullWorkoutPlanDto.fromDomain(optionalPlan.get());
    }

    private Optional<WorkoutPlan> getPlan(UUID workoutPlanID) {
        return this.workoutRepositoryAdapter.getPlanById(workoutPlanID);
    }
}
