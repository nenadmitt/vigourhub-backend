package com.vigourhub.backend.dto.workout_plans;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vigourhub.backend.domain.entity.workout_plans.RoutineWorkout;
import com.vigourhub.backend.dto.workouts.WorkoutResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class RoutineWorkoutResponseDTO {

    private UUID id;
    private WorkoutResponseDTO workout;
    private List<WorkingSetDTO> workingSets;
    private String executionType;

}
