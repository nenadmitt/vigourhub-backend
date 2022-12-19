package com.vigourhub.backend.domain.entity.account;

import com.vigourhub.backend.domain.entity.AuditEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "accounts")
@Getter
@Setter
public class Account extends AuditEntity {

    @Column(name = "name", unique = true)
    private String name;
    private boolean isTrial;
    private LocalDateTime trialStarted;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;
}
