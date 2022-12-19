package com.vigourhub.backend.dto.workout_plans;

import com.vigourhub.backend.domain.entity.enums.WorkoutExecutionType;
import com.vigourhub.backend.infrastructure.validators.ValidUUID;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class RoutineWorkoutRequestDTO {

    @NotNull
    @ValidUUID
    private String workoutId;
    private String note;
    @NotNull
    private WorkoutExecutionType executionType;
    @NotNull
    private List<WorkingSetDTO> workingSets;
}
