package com.vigourhub.backend.dto.workouts;

import com.vigourhub.backend.domain.entity.enums.WorkoutType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutRequestDTO {

    private String name;
    private WorkoutType type;
    private String imageUrl;
    private String videoUrl;
}
