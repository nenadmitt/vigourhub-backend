package com.vigourhub.backend.domain.account;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "subscriptions")
public class Subscription {

    @Id
    private UUID id;
    private String name;
    private Float price;
    private Integer maxUsers;
}
