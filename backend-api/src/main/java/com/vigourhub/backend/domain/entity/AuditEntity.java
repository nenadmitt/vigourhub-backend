package com.vigourhub.backend.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class AuditEntity extends BaseEntity{

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AuditEntity() {
        this.createdAt = LocalDateTime.now();
    }
}
