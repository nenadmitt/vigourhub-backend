package com.vigourhub.backend.domain.models.adapters.impl;

import com.vigourhub.backend.domain.models.adapters.WorkoutsRepositoryAdapter;
import com.vigourhub.backend.domain.models.workouts.RoutineWorkout;
import com.vigourhub.backend.domain.models.workouts.Workout;
import com.vigourhub.backend.domain.models.workouts.WorkoutPlan;
import com.vigourhub.backend.domain.models.workouts.WorkoutRoutine;
import com.vigourhub.backend.domain.repository.RoutineWorkoutRepository;
import com.vigourhub.backend.domain.repository.WorkoutPlanRepository;
import com.vigourhub.backend.domain.repository.WorkoutRepository;
import com.vigourhub.backend.domain.repository.WorkoutRoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class WorkoutsRepositoryAdapterImpl implements WorkoutsRepositoryAdapter {

    private WorkoutRepository workoutRepository;
    private WorkoutPlanRepository workoutPlanRepository;
    private WorkoutRoutineRepository routineRepository;
    private RoutineWorkoutRepository routineWorkoutRepository;

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
}
