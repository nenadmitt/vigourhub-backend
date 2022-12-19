package com.vigourhub.backend.dto.workout_plans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkingSetDTO {
    private Integer setCount;
    private Integer setPosition;
    private Integer repetitions;

    private Float load;
    private Integer repsInReserve;
    private Integer timeInSeconds;
    private Integer distance;
}
