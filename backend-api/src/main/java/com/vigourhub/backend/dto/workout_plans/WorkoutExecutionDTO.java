package com.vigourhub.backend.dto.workout_plans;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutExecutionDTO {
    private Float load;
    private Integer index;
    private Integer repetitions;
    private Integer sets;
    private Integer timeInSeconds;
    private Integer distance;
    private Integer repsInReserve;

}
