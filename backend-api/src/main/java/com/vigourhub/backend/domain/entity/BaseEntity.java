package com.vigourhub.backend.domain.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    @Id
    UUID id;

    public BaseEntity() {
        this.id = UUID.randomUUID();
    }
}
