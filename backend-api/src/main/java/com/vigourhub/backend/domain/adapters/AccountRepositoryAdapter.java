package com.vigourhub.backend.domain.adapters;

import com.vigourhub.backend.domain.models.Account;
import com.vigourhub.backend.domain.models.ClientInvitation;
import com.vigourhub.backend.domain.models.Role;
import com.vigourhub.backend.domain.models.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface AccountRepositoryAdapter {

    void insert(Account account);
    void insert(User user);

    void insert(ClientInvitation invitation);

    Optional<User> findUserByUsername(String username);

    Optional<Role> findRoleByName(String name);

    Optional<ClientInvitation> findInvitationById(String id);
}
