package com.vigourhub.backend.dto.workout_plans;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WorkoutPlanRequest {
    @NotBlank
    private String name;
}
