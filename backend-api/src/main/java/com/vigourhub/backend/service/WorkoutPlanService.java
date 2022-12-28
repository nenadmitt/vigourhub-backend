package com.vigourhub.backend.service;

import com.vigourhub.backend.dto.IdResponseDto;
import com.vigourhub.backend.dto.workout_plans.RoutineRequestDTO;
import com.vigourhub.backend.dto.workout_plans.RoutineWorkoutRequestDTO;
import com.vigourhub.backend.dto.workout_plans.WorkoutPlanResponseDTO;
import com.vigourhub.backend.dto.workout_plans.WorkoutPlanRequestDTO;
import com.vigourhub.backend.infrastructure.exceptions.NotFoundException;

import java.util.UUID;

public interface WorkoutPlanService {
    IdResponseDto createWorkoutPlan(WorkoutPlanRequestDTO request);

    IdResponseDto createWorkoutRoutine(UUID workoutPlanId, RoutineRequestDTO requestDTO) throws Exception;

    IdResponseDto addRoutineWorkout(UUID workoutPlanId, UUID routineId, RoutineWorkoutRequestDTO request) throws NotFoundException, Exception;

    WorkoutPlanResponseDTO getFullWorkoutPlan(UUID workoutPlanId) throws NotFoundException;
}
