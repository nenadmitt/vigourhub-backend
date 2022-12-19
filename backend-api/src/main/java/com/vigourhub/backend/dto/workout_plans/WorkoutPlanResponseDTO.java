package com.vigourhub.backend.dto.workout_plans;
import com.vigourhub.backend.domain.entity.workout_plans.WorkoutPlan;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class WorkoutPlanResponseDTO {

    private String id;
    private String name;
    private List<RoutineResponseDTO> routines;

}
