package com.vigourhub.backend.service;

import com.vigourhub.backend.dto.PagedResponse;
import com.vigourhub.backend.dto.workout_plans.WorkoutDto;
import org.springframework.data.domain.PageRequest;

public interface WorkoutService {
    PagedResponse<WorkoutDto> getPagedWorkouts(PageRequest request);
}
