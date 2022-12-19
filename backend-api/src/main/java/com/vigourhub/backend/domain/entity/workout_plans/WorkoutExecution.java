package com.vigourhub.backend.domain.entity.workout_plans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutExecution {

    private Integer index;
    private Integer repetitions;
    private Integer sets;
    private Float load;
    private Integer repsInReserve;
    private Integer timeInSeconds;
    private Integer distance;

}
