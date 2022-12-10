package com.vigourhub.backend.domain.models.workout_plans;

import com.vigourhub.backend.domain.models.AuditEntity;
import com.vigourhub.backend.domain.models.account.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name = "workout_plans")
@Getter
@Setter
@NoArgsConstructor
public class WorkoutPlan extends AuditEntity {

    @Id
    private UUID id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User creator;
    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignee;
    private boolean completed;
    @OneToMany(mappedBy = "workoutPlan")
    private List<WorkoutRoutine> routines;

}
