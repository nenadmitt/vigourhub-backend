package com.vigourhub.backend.service;

import com.vigourhub.backend.dto.IdResponseDto;
import com.vigourhub.backend.dto.workout_plans.FullWorkoutPlanDto;
import com.vigourhub.backend.dto.workout_plans.WorkoutPlanRequest;
import com.vigourhub.backend.infrastructure.exceptions.NotFoundException;

import java.util.UUID;

public interface WorkoutPlanService {
    IdResponseDto createWorkoutPlan(WorkoutPlanRequest request);

    IdResponseDto createWorkoutRoutine(UUID workoutPlanId, String name) throws Exception;

    IdResponseDto addRoutineWorkout(UUID workoutPlanId, UUID routineId) throws NotFoundException, Exception;

    FullWorkoutPlanDto getFullWorkoutPlan(UUID workoutPlanId) throws NotFoundException;
}
