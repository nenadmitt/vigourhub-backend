package com.vigourhub.backend.domain.adapters;

import com.vigourhub.backend.domain.models.workout_plans.RoutineWorkout;
import com.vigourhub.backend.domain.models.workouts.Workout;
import com.vigourhub.backend.domain.models.workout_plans.WorkoutPlan;
import com.vigourhub.backend.domain.models.workout_plans.WorkoutRoutine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    Page<Workout> getPagedWorkoutsForAccount(UUID accountID, PageRequest request);
}
