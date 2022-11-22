package com.vigourhub.backend.domain.models.adapters;

import com.vigourhub.backend.domain.models.account.Account;
import com.vigourhub.backend.domain.models.account.ClientInvitation;
import com.vigourhub.backend.domain.models.account.Role;
import com.vigourhub.backend.domain.models.account.User;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public interface AccountRepositoryAdapter {

    void insert(Account account);
    void insert(User user);

    void insert(ClientInvitation invitation);

    Optional<User> findUserByUsername(String username);

    Optional<Role> findRoleByName(String name);

    Optional<ClientInvitation> findInvitationById(String id);

    Optional<ClientInvitation> findInvitationByUsername(String username);

    void removeInvitation(UUID id);
}