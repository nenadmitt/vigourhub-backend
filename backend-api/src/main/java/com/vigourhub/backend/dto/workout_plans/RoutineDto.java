package com.vigourhub.backend.dto.workout_plans;

import com.vigourhub.backend.domain.models.workout_plans.WorkoutRoutine;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RoutineDto {

    private String id;
    private String name;
    private List<RoutineWorkoutDto> workouts;

    public static RoutineDto fromDomain(WorkoutRoutine domain) {
        var response = new RoutineDto();
        response.setId(domain.getId().toString());
        response.setName(domain.getName());

        var routineWorkouts = domain.getWorkouts();
        var routineWorkoutsResponse = new ArrayList<RoutineWorkoutDto>(routineWorkouts.size());

        for (var i = 0; i < routineWorkouts.size(); i++) {
            var workoutResponse = RoutineWorkoutDto.fromDomain(routineWorkouts.get(i));
            routineWorkoutsResponse.add(workoutResponse);
        }
        response.setWorkouts(routineWorkoutsResponse);
        return response;
    }
}
