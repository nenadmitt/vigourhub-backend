package com.vigourhub.backend.domain.models.workout_plans;

import com.vigourhub.backend.domain.converters.WorkoutMetadataConverter;
import com.vigourhub.backend.domain.models.AuditEntity;
import com.vigourhub.backend.domain.models.workouts.Workout;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "routine_workouts")
@Getter
@Setter
public class RoutineWorkout extends AuditEntity {

    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "routine_id")
    private WorkoutRoutine routine;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;

    @Convert(converter = WorkoutMetadataConverter.class)
    @Column(columnDefinition = "jsonb")
    private WorkoutMetadata metadata;

    private String executionType;

}
