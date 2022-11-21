package com.vigourhub.backend.service.impl;

import com.vigourhub.backend.domain.adapters.AccountRepositoryAdapter;
import com.vigourhub.backend.domain.models.Account;
import com.vigourhub.backend.domain.models.ClientInvitation;
import com.vigourhub.backend.domain.models.Role;
import com.vigourhub.backend.domain.models.User;
import com.vigourhub.backend.dto.accounts.ClientInvitationDto;
import com.vigourhub.backend.dto.users.UserDto;
import com.vigourhub.backend.infrastructure.exceptions.ConflictException;
import com.vigourhub.backend.infrastructure.exceptions.InternalServerError;
import com.vigourhub.backend.infrastructure.exceptions.NotFoundException;
import com.vigourhub.backend.infrastructure.security.SecurityUtils;
import com.vigourhub.backend.infrastructure.security.keycloak.KeycloakContext;
import com.vigourhub.backend.dto.keycloak.KeycloakUser;
import com.vigourhub.backend.service.AccountService;
import com.vigourhub.backend.dto.accounts.AccountRequestDto;
import com.vigourhub.backend.dto.accounts.AccountResponseDto;
import com.vigourhub.backend.dto.accounts.AdminUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {

    private final String ROLE_INSTRUCTOR = "Instructor";
    private final String ROLE_CLIENT = "Client";
    private final AccountRepositoryAdapter accountAdapter;
    private KeycloakContext keycloakContext;

    @Autowired
    public AccountServiceImpl( AccountRepositoryAdapter accountAdapter, KeycloakContext keycloakContext) {
        this.accountAdapter = accountAdapter;
        this.keycloakContext = keycloakContext;
    }

    @Override
    @Transactional
    public AccountResponseDto createAccount(AccountRequestDto request) throws Exception {

        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setName(request.getName());
        account.setTrial(true);
        account.setTrialStarted(LocalDateTime.now());
        account.setCreatedAt(LocalDateTime.now());

        accountAdapter.insert(account);

        var username = request.getUser().getUsername();

        Optional<User> present = accountAdapter.findUserByUsername(username);
        if (present.isPresent()) {
            throw new ConflictException(String.format("User with username %s already exists", username));
        }

        AdminUserRequest userRequest = request.getUser();

        KeycloakUser keycloakUser = new KeycloakUser(
                userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getUsername(),
                userRequest.getPassword()
        );

        try {
            this.keycloakContext.createKeycloakUser(keycloakUser);

            User user = new User();
            user.setId(UUID.randomUUID());
            user.setUsername(userRequest.getUsername());
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            user.setAccount(account);

            Optional<Role> instructorRole = accountAdapter.findRoleByName(ROLE_INSTRUCTOR);
            List<Role> roles = new ArrayList<>();
            roles.add(instructorRole.get());

            user.setRoles(roles);

            accountAdapter.insert(user);
        }catch (Exception ex) {
            throw new InternalServerError(ex.getMessage());
        }

        return null;
    }

    @Override
    public ClientInvitationDto generateClientInvitation(String username) throws Exception {

        Optional<User> exists = this.accountAdapter.findUserByUsername(username);
        if (exists.isPresent()) {
            throw new ConflictException(String.format("User with username %s, already present", username));
        }

        var principal = SecurityUtils.getCurrentPrincipal();

        ClientInvitation invitation = new ClientInvitation();
        invitation.setAccountId(UUID.fromString(principal.getAccountId()));
        invitation.setUsername(username);
        invitation.setId(UUID.randomUUID());

        accountAdapter.insert(invitation);

        ClientInvitationDto dto = new ClientInvitationDto();
        dto.setToken(invitation.getId().toString());

        return dto;
    }

    public String registerInvitedClient(String token, UserDto userDto) throws Exception {

        Optional<ClientInvitation> invitation = this.accountAdapter.findInvitationById(token);

        if (invitation.isEmpty()) {
            throw new NotFoundException("Invitation not found");
        }

        var _invitation = invitation.get();
        User client = new User();
        client.setUsername(_invitation.getUsername());
        client.setId(UUID.randomUUID());
        client.setFirstName(userDto.getFirstName());
        client.setLastName(userDto.getLastName());

        var account = new Account();
        account.setId(_invitation.getAccountId());

        client.setAccount(account);

        Optional<Role> clientRole = accountAdapter.findRoleByName(ROLE_CLIENT);
        if (clientRole.isEmpty()) {
            throw new InternalServerError("Client role not found in a database");
        }
        List<Role> roles = Arrays.asList(clientRole.get());
        client.setRoles(roles);

        accountAdapter.insert(client);

        return client.getId().toString();
    }
}
