package com.vigourhub.backend.domain.entity.workout_plans;

import com.vigourhub.backend.domain.converters.WorkingSetConverter;
import com.vigourhub.backend.domain.entity.AuditEntity;
import com.vigourhub.backend.domain.entity.BaseEntity;
import com.vigourhub.backend.domain.entity.enums.WorkoutExecutionType;
import com.vigourhub.backend.domain.entity.workouts.Workout;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "routine_workouts")
@Getter
@Setter
public class RoutineWorkout extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "routine_id")
    private WorkoutRoutine routine;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;

    @Enumerated(EnumType.STRING)
    @Column(name="execution_type")
    private WorkoutExecutionType workoutExecutionType;

    @Convert(converter = WorkingSetConverter.class)
    @Column(columnDefinition = "jsonb", name="workings_sets")
    private List<WorkingSet> workingSets;
}
