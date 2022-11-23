package com.vigourhub.backend.domain.adapters.impl;

import com.vigourhub.backend.domain.adapters.AccountRepositoryAdapter;
import com.vigourhub.backend.domain.models.account.Account;
import com.vigourhub.backend.domain.models.account.ClientInvitation;
import com.vigourhub.backend.domain.models.account.Role;
import com.vigourhub.backend.domain.models.account.User;
import com.vigourhub.backend.domain.repository.AccountRepository;
import com.vigourhub.backend.domain.repository.ClientInvitationRepository;
import com.vigourhub.backend.domain.repository.RoleRepository;
import com.vigourhub.backend.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class AccountRepositoryAdapterImpl implements AccountRepositoryAdapter {

    private AccountRepository accountRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ClientInvitationRepository invitationRepository;

    @Autowired
    public AccountRepositoryAdapterImpl(AccountRepository accountRepository, UserRepository userRepository, RoleRepository roleRepository, ClientInvitationRepository clientInvitationRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.invitationRepository = clientInvitationRepository;
    }

    @Override
    public void insert(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void insert(User user) {
        userRepository.save(user);
    }

    public void insert(ClientInvitation invitation) {
        invitationRepository.save(invitation);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public Optional<Role> findRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }

    public Optional<ClientInvitation> findInvitationById(String id) {
        return this.invitationRepository.findById(UUID.fromString(id));
    }

    public Optional<ClientInvitation> findInvitationByUsername(String username) {
        return this.invitationRepository.findByUsername(username);
    }

    @Override
    public void removeInvitation(UUID id) {
        this.invitationRepository.deleteById(id);
    }
}
