package com.vigourhub.backend.dto.workout_plans;

import com.vigourhub.backend.domain.models.workouts.Workout;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutDto {

    private String id;
    private String name;
    private String type;
    private boolean isSystem;

    public static WorkoutDto fromDomain(Workout domain) {

        var response = new WorkoutDto();
        response.setId(domain.getId().toString());
        response.setName(domain.getName());
        response.setType(domain.getType().toString());

        return response;
    }
}
