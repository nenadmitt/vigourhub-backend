package com.vigourhub.backend.domain.account;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "accounts")
@Getter
@Setter
@ToString
public class Account extends AuditEntity {

    @Id
    private UUID id;
    @Column(name = "name", unique = true)
    private String name;
    private boolean isTrial;
    private LocalDateTime trialStarted;
    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;
}
