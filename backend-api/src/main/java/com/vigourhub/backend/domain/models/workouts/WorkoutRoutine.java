package com.vigourhub.backend.domain.models.workouts;

import com.vigourhub.backend.domain.models.AuditEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name="workout_routines")
@Getter
@Setter
public class WorkoutRoutine extends AuditEntity {

    @Id
    private UUID id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "workout_plan_id")
    private WorkoutPlan workoutPlan;
    @OneToMany(mappedBy = "routine")
    private List<RoutineWorkout> workouts;
}
