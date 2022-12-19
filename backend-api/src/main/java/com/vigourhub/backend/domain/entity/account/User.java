package com.vigourhub.backend.domain.entity.account;

import com.vigourhub.backend.domain.entity.AuditEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name = "users")
@Getter
@Setter
public class User extends AuditEntity {

    private String username;
    private String firstName;
    private String lastName;
    private UUID keycloakId;
    private boolean emailApproved;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
