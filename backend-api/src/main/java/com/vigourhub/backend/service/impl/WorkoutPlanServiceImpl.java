package com.vigourhub.backend.service.impl;

import com.vigourhub.backend.domain.adapters.WorkoutPlansRepositoryAdapter;
import com.vigourhub.backend.domain.entity.account.Account;
import com.vigourhub.backend.domain.entity.account.User;
import com.vigourhub.backend.domain.entity.workout_plans.*;
import com.vigourhub.backend.dto.IdResponseDto;
import com.vigourhub.backend.dto.workout_plans.RoutineRequestDTO;
import com.vigourhub.backend.dto.workout_plans.RoutineWorkoutRequestDTO;
import com.vigourhub.backend.dto.workout_plans.WorkoutPlanResponseDTO;
import com.vigourhub.backend.dto.workout_plans.WorkoutPlanRequestDTO;
import com.vigourhub.backend.infrastructure.exceptions.ForbiddenException;
import com.vigourhub.backend.infrastructure.exceptions.NotFoundException;
import com.vigourhub.backend.security.SecurityUtils;
import com.vigourhub.backend.service.WorkoutPlanService;
import com.vigourhub.backend.service.mapper.WorkoutPlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class WorkoutPlanServiceImpl implements WorkoutPlanService {

    private WorkoutPlansRepositoryAdapter workoutRepositoryAdapter;

    private WorkoutPlanMapper workoutPlanMapper;

    @Autowired
    public WorkoutPlanServiceImpl(WorkoutPlansRepositoryAdapter workoutRepositoryAdapter, WorkoutPlanMapper workoutPlanMapper) {
        this.workoutRepositoryAdapter = workoutRepositoryAdapter;
        this.workoutPlanMapper = workoutPlanMapper;
    }

    @Override
    public IdResponseDto createWorkoutPlan(WorkoutPlanRequestDTO request) {

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
    public IdResponseDto createWorkoutRoutine(UUID workoutPlanId, RoutineRequestDTO requestDTO) throws Exception {

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
        routine.setName(requestDTO.getName());
        routine.setWorkoutPlan(plan);
        routine.setId(UUID.randomUUID());

        this.workoutRepositoryAdapter.insert(routine);


        return new IdResponseDto(routine.getId().toString());
    }

    @Override
    public IdResponseDto addRoutineWorkout(UUID workoutPlanId, UUID routineId, RoutineWorkoutRequestDTO requestDTO) throws Exception {

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

        var routine = optionalRoutine.get();

        var workoutID = UUID.fromString(requestDTO.getWorkoutId());
        var optionalWorkout = workoutRepositoryAdapter.getWorkoutById(workoutID);


        if (optionalWorkout.isEmpty()) {
            throw new NotFoundException("Workout not found");
        }

        var workingSetsDTO = requestDTO.getWorkingSets();
        List<WorkingSet> sets = new ArrayList<>(workingSetsDTO.size());

        AtomicInteger i = new AtomicInteger(1);
        workingSetsDTO.forEach(dto -> {
            sets.add(workoutPlanMapper.toWorkingSet(dto));
        });

        RoutineWorkout workout = new RoutineWorkout();
        workout.setId(UUID.randomUUID());
        workout.setRoutine(routine);
        workout.setWorkout(optionalWorkout.get());
        workout.setWorkingSets(sets);
        workout.setWorkoutExecutionType(requestDTO.getExecutionType());

        this.workoutRepositoryAdapter.insert(workout);
        return new IdResponseDto(workout.getId().toString());
    }

    @Override
    public WorkoutPlanResponseDTO getFullWorkoutPlan(UUID workoutPlanId) throws NotFoundException {

        Optional<WorkoutPlan> optionalPlan = workoutRepositoryAdapter.getPlanById(workoutPlanId);

        if (optionalPlan.isEmpty()) {
            throw new NotFoundException("Plan not found");
        }
        var workout = optionalPlan.get().getRoutines().get(0).getWorkouts().get(0);

        System.out.println(workout.getWorkingSets());
        System.out.println(workout.getWorkingSets().getClass().getName());

        return workoutPlanMapper.toPlanResponse(optionalPlan.get());
    }

    private Optional<WorkoutPlan> getPlan(UUID workoutPlanID) {
        return this.workoutRepositoryAdapter.getPlanById(workoutPlanID);
    }
}
