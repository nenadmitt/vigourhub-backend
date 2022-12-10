package com.vigourhub.backend.domain.models.workout_plans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutMetadata {
    int position;
    int set;
    int repetitions;
    float load;
}
