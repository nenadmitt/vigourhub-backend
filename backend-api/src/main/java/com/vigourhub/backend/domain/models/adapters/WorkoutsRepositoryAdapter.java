package com.vigourhub.backend.domain.models.adapters;

import com.vigourhub.backend.domain.models.workouts.RoutineWorkout;
import com.vigourhub.backend.domain.models.workouts.Workout;
import com.vigourhub.backend.domain.models.workouts.WorkoutPlan;
import com.vigourhub.backend.domain.models.workouts.WorkoutRoutine;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public interface WorkoutsRepositoryAdapter {

    void insert(WorkoutPlan plan);

    Optional<WorkoutPlan> getPlanById(UUID workoutPlanID);

    void insert(WorkoutRoutine routine);

    Optional<WorkoutRoutine> getRoutineById(UUID routineId);

    void insert(RoutineWorkout workout);

    Optional<Workout> getWorkoutById(UUID id);
}
