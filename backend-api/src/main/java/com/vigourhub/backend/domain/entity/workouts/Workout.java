package com.vigourhub.backend.domain.entity.workouts;

import com.vigourhub.backend.domain.entity.AuditEntity;
import com.vigourhub.backend.domain.entity.account.Account;
import com.vigourhub.backend.domain.entity.enums.WorkoutType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "workouts")
@Getter
@Setter
@NoArgsConstructor
public class Workout extends AuditEntity {

    private String name;
    @Enumerated(EnumType.STRING)
    private WorkoutType type;
    private boolean isSystem;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany
    @JoinColumn(referencedColumnName = "id",name = "workout_id")
    private List<WorkoutVideo> videos;

}
