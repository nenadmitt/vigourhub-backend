package com.vigourhub.backend.dto.workouts;

import com.vigourhub.backend.domain.entity.workouts.Workout;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class WorkoutResponseDTO {

    private String id;
    private String name;
    private String createdBy;
    private String accountId;
    private LocalDateTime createdAt;

    public static WorkoutResponseDTO fromDomain(Workout domain){
        WorkoutResponseDTO response = new WorkoutResponseDTO();
        response.setId(domain.getId().toString());
        response.setName(domain.getName());
        response.setCreatedAt(domain.getCreatedAt());
        return response;
    }
}
