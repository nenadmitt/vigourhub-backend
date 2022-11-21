package com.vigourhub.backend.domain.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name = "users")
@Getter
@Setter
public class User extends AuditEntity{
    @Id
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;

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
