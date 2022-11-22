package com.vigourhub.backend.domain.models.workouts;

import com.vigourhub.backend.domain.converters.WorkoutMetadataConverter;
import com.vigourhub.backend.domain.models.AuditEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;
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

}
