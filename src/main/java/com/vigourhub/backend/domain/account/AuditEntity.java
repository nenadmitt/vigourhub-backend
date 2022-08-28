package com.vigourhub.backend.domain.account;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuditEntity {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
