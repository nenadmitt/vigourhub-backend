package com.vigourhub.backend.domain.adapters.impl;

import com.vigourhub.backend.domain.adapters.WorkoutsRepositoryAdapter;
import com.vigourhub.backend.domain.models.workout_plans.RoutineWorkout;
import com.vigourhub.backend.domain.models.workouts.Workout;
import com.vigourhub.backend.domain.models.workout_plans.WorkoutPlan;
import com.vigourhub.backend.domain.models.workout_plans.WorkoutRoutine;
import com.vigourhub.backend.domain.repository.RoutineWorkoutRepository;
import com.vigourhub.backend.domain.repository.WorkoutPlanRepository;
import com.vigourhub.backend.domain.repository.WorkoutRepository;
import com.vigourhub.backend.domain.repository.WorkoutRoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class WorkoutsRepositoryAdapterImpl implements WorkoutsRepositoryAdapter {

    private final WorkoutRepository workoutRepository;
    private final WorkoutPlanRepository workoutPlanRepository;
    private final WorkoutRoutineRepository routineRepository;
    private final RoutineWorkoutRepository routineWorkoutRepository;

    @Autowired
    public WorkoutsRepositoryAdapterImpl(WorkoutRepository workoutRepository, WorkoutPlanRepository workoutPlanRepository, WorkoutRoutineRepository routineRepository, RoutineWorkoutRepository routineWorkoutRepository) {
        this.workoutRepository = workoutRepository;
        this.workoutPlanRepository = workoutPlanRepository;
        this.routineRepository = routineRepository;
        this.routineWorkoutRepository = routineWorkoutRepository;
    }

    @Override
    public void insert(WorkoutPlan plan) {
        this.workoutPlanRepository.save(plan);
    }

    @Override
    public Optional<WorkoutPlan> getPlanById(UUID workoutPlanID) {
        return this.workoutPlanRepository.findById(workoutPlanID);
    }

    @Override
    public void insert(WorkoutRoutine routine) {
        this.routineRepository.save(routine);
    }

    @Override
    public Optional<WorkoutRoutine> getRoutineById(UUID routineId) {
        return this.routineRepository.findById(routineId);
    }

    @Override
    public void insert(RoutineWorkout workout) {
        this.routineWorkoutRepository.save(workout);
    }

    @Override
    public Optional<Workout> getWorkoutById(UUID id) {
        return this.workoutRepository.findById(id);
    }

    @Override
    public Page<Workout> getPagedWorkoutsForAccount(UUID accountID, PageRequest request) {
        return workoutRepository.findAllByAccountIdOrIsSystemTrue(accountID, request);
    }

}
