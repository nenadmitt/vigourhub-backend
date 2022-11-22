package com.vigourhub.backend.dto.workout_plans;

import com.vigourhub.backend.domain.models.workouts.RoutineWorkout;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoutineWorkoutDto {

    private String id;
    private WorkoutDto workout;
    private MetadataDto metadata;

    public static RoutineWorkoutDto fromDomain(RoutineWorkout domain) {

        if (domain.getWorkout() == null) {
            System.out.println("Workout is null!");
        }
        RoutineWorkoutDto response = new RoutineWorkoutDto();
        var workoutResponse = WorkoutDto.fromDomain(domain.getWorkout());
        var metadata = MetadataDto.fromDomain(domain.getMetadata());
        response.setMetadata(metadata);
        response.setWorkout(workoutResponse);
        response.setId(domain.getId().toString());

        return response;
    }
}
