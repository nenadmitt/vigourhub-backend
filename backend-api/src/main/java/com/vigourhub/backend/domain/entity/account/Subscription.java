package com.vigourhub.backend.domain.entity.account;

import com.vigourhub.backend.domain.entity.AuditEntity;
import com.vigourhub.backend.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "subscriptions")
public class Subscription extends BaseEntity {

    private UUID id;
    private String name;
    private Float price;
    private Integer maxUsers;
}
