package com.vigourhub.backend.domain.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class ClientInvitation extends AuditEntity{
    @Id
    private UUID id;
    private UUID accountId;
    private String username;
}
