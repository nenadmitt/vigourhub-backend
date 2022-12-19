package com.vigourhub.backend.service;

import com.vigourhub.backend.dto.PagedResponse;
import com.vigourhub.backend.dto.workout_plans.WorkoutDto;
import com.vigourhub.backend.dto.workouts.WorkoutRequestDTO;
import com.vigourhub.backend.dto.workouts.WorkoutResponseDTO;
import org.springframework.data.domain.PageRequest;

public interface WorkoutService {
    PagedResponse<WorkoutResponseDTO> getPagedWorkouts(PageRequest request);

    WorkoutResponseDTO createWorkout(WorkoutRequestDTO request);
}
