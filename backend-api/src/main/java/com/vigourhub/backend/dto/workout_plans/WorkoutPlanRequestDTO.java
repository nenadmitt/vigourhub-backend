package com.vigourhub.backend.dto.workout_plans;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class WorkoutPlanRequestDTO {
    @NotBlank(message = "Name cannot be blank")
    @Size(min=3, max=50, message = "Workout plan name must be between 3 and 50 characters")
    private String name;
}
