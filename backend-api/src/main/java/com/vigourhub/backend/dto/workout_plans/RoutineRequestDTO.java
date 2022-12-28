package com.vigourhub.backend.dto.workout_plans;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RoutineRequestDTO {

    @NotBlank
    private String name;
}
