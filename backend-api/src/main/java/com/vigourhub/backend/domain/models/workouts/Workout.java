package com.vigourhub.backend.domain.models.workouts;

import com.vigourhub.backend.domain.models.AuditEntity;
import com.vigourhub.backend.domain.models.account.Account;
import com.vigourhub.backend.domain.models.account.User;
import com.vigourhub.backend.domain.models.enums.WorkoutType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "workouts")
@Getter
@Setter
@NoArgsConstructor
public class Workout extends AuditEntity {

    @Id
    private UUID id;
    private String name;
    @Enumerated(EnumType.STRING)
    private WorkoutType type;
    private boolean isSystem;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

}
