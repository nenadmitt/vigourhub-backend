package com.vigourhub.backend.domain.entity.workout_plans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkingSet {

    private Integer setCount;
    private Integer setPosition;
    private Integer repetitions;

    private Float load;
    private Integer repsInReserve;
    private Integer timeInSeconds;
    private Integer distance;


}
