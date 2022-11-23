package com.vigourhub.backend.domain.models.workouts;

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
