package com.vigourhub.backend.domain.entity.workout_plans;

import com.vigourhub.backend.domain.entity.AuditEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name="workout_routines")
@Getter
@Setter
public class WorkoutRoutine extends AuditEntity {

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_plan_id")
    private WorkoutPlan workoutPlan;
    @OneToMany(mappedBy = "routine")
    private List<RoutineWorkout> workouts;
}
