package com.vigourhub.backend.domain.models.account;

import com.vigourhub.backend.domain.models.AuditEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name="client_invitations")
@Data
public class ClientInvitation extends AuditEntity {
    @Id
    private UUID id;
    private UUID accountId;
    private String username;
}
