package com.vigourhub.backend.domain.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "roles")
@Getter
@Setter
public class Role {
    @Id
    private UUID id;
    private String name;
}
