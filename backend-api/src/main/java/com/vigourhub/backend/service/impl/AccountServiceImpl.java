package com.vigourhub.backend.service.impl;

import com.vigourhub.backend.domain.models.adapters.AccountRepositoryAdapter;
import com.vigourhub.backend.domain.models.account.Account;
import com.vigourhub.backend.domain.models.account.ClientInvitation;
import com.vigourhub.backend.domain.models.account.Role;
import com.vigourhub.backend.domain.models.account.User;
import com.vigourhub.backend.dto.IdResponseDto;
import com.vigourhub.backend.dto.accounts.*;
import com.vigourhub.backend.infrastructure.exceptions.ConflictException;
import com.vigourhub.backend.infrastructure.exceptions.InternalServerError;
import com.vigourhub.backend.infrastructure.exceptions.NotFoundException;
import com.vigourhub.backend.infrastructure.security.SecurityUtils;
import com.vigourhub.backend.infrastructure.security.keycloak.KeycloakContext;
import com.vigourhub.backend.dto.keycloak.KeycloakUser;
import com.vigourhub.backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

@Service
public class AccountServiceImpl implements AccountService {

    private final String ROLE_INSTRUCTOR = "Instructor";
    private final String ROLE_CLIENT = "Client";
    private final AccountRepositoryAdapter accountAdapter;
    private KeycloakContext keycloakContext;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

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

        Optional<ClientInvitation> invitation = this.accountAdapter.findInvitationByUsername(username);

        if (invitation.isPresent()) {
            var _invitation = invitation.get();
            ClientInvitationDto dto = new ClientInvitationDto();
            dto.setToken(_invitation.getId().toString());

            return dto;
        }

        var principal = SecurityUtils.getCurrentPrincipal();

        ClientInvitation _invitation = new ClientInvitation();
        _invitation.setAccountId(UUID.fromString(principal.getAccountId()));
        _invitation.setUsername(username);
        _invitation.setId(UUID.randomUUID());

        accountAdapter.insert(_invitation);

        ClientInvitationDto dto = new ClientInvitationDto();
        dto.setToken(_invitation.getId().toString());

        return dto;
    }

    @Override
    @Transactional
    public IdResponseDto registerInvitedClient(String token, ClientRegistrationRequestDto userDto) throws Exception {

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

        KeycloakUser keycloakUser = new KeycloakUser(
                userDto.getFirstName(),
                userDto.getLastName(),
                _invitation.getUsername(),
                userDto.getPassword()
        );

        try {
            keycloakContext.createKeycloakUser(keycloakUser);
        }catch (Exception ex) {
            logger.info(ex.getMessage());
            throw new InternalServerError(ex.getMessage());
        }

        var account = new Account();
        account.setId(_invitation.getAccountId());

        client.setAccount(account);

        Optional<Role> clientRole = accountAdapter.findRoleByName(ROLE_CLIENT);
        if (clientRole.isEmpty()) {
            throw new InternalServerError("Client role not found in a database");
        }
        List<Role> roles = List.of(clientRole.get());
        client.setRoles(roles);

        accountAdapter.insert(client);
        var userId = client.getId().toString();

        this.accountAdapter.removeInvitation(_invitation.getId());

        return new IdResponseDto(userId);
    }
}
