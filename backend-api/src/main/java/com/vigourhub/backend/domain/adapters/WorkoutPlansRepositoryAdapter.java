package com.vigourhub.backend.domain.adapters;

import com.vigourhub.backend.domain.entity.workout_plans.RoutineWorkout;
import com.vigourhub.backend.domain.entity.workouts.Workout;
import com.vigourhub.backend.domain.entity.workout_plans.WorkoutPlan;
import com.vigourhub.backend.domain.entity.workout_plans.WorkoutRoutine;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public interface WorkoutPlansRepositoryAdapter {

    void insert(WorkoutPlan plan);

    Optional<WorkoutPlan> getPlanById(UUID workoutPlanID);

    void insert(WorkoutRoutine routine);

    Optional<WorkoutRoutine> getRoutineById(UUID routineId);

    void insert(RoutineWorkout workout);

    Optional<Workout> getWorkoutById(UUID id);

}
