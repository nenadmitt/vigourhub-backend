package com.vigourhub.backend.domain.adapters;

import com.vigourhub.backend.domain.entity.account.Account;
import com.vigourhub.backend.domain.entity.account.ClientInvitation;
import com.vigourhub.backend.domain.entity.account.Role;
import com.vigourhub.backend.domain.entity.account.User;
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
