package com.vigourhub.backend.dto.workout_plans;
import com.vigourhub.backend.domain.models.workout_plans.WorkoutPlan;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FullWorkoutPlanDto {

    private String id;
    private String name;
    private List<RoutineDto> routines;

    public static FullWorkoutPlanDto fromDomain(WorkoutPlan plan) {

        var planResponse = new FullWorkoutPlanDto();
        planResponse.setId(plan.getId().toString());
        planResponse.setName(plan.getName());

        var routines = plan.getRoutines();
        var routinesResponse = new ArrayList<RoutineDto>(routines.size());

        for (int i = 0; i < routines.size(); i++) {
            var routineResponse = RoutineDto.fromDomain(routines.get(i));
            routinesResponse.add(routineResponse);
        }
        planResponse.setRoutines(routinesResponse);

        return planResponse;
    }
}
