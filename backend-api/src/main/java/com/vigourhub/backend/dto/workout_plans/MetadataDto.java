package com.vigourhub.backend.dto.workout_plans;

import com.vigourhub.backend.domain.models.workout_plans.WorkoutMetadata;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MetadataDto {

    private int sets;
    private int repetitions;
    private float load;

    public static MetadataDto fromDomain(WorkoutMetadata domain) {
        return new MetadataDto(domain.getSet(), domain.getRepetitions(), domain.getLoad());
    }
}
