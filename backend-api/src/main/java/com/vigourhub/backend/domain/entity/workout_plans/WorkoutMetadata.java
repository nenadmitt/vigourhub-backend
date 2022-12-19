package com.vigourhub.backend.domain.entity.workout_plans;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WorkoutMetadata {
    private String executionType;
    private List<WorkoutExecution> execution;
}
