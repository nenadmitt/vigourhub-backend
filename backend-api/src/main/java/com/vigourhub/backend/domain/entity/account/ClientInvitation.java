package com.vigourhub.backend.domain.entity.account;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.vigourhub.backend.domain.entity.AuditEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Entity(name="client_invitations")
@Getter
@Setter
public class ClientInvitation extends AuditEntity {

    private UUID accountId;
    private String username;
}
