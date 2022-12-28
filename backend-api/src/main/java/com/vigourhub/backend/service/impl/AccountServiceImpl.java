package com.vigourhub.backend.service.impl;

import com.vigourhub.backend.domain.adapters.AccountRepositoryAdapter;
import com.vigourhub.backend.domain.entity.account.Account;
import com.vigourhub.backend.domain.entity.account.ClientInvitation;
import com.vigourhub.backend.domain.entity.account.Role;
import com.vigourhub.backend.domain.entity.account.User;
import com.vigourhub.backend.dto.IdResponseDto;
import com.vigourhub.backend.dto.accounts.*;
import com.vigourhub.backend.dto.accounts.UserResponseDTO;
import com.vigourhub.backend.infrastructure.exceptions.ConflictException;
import com.vigourhub.backend.infrastructure.exceptions.InternalServerError;
import com.vigourhub.backend.infrastructure.exceptions.NotFoundException;
import com.vigourhub.backend.security.SecurityUtils;
import com.vigourhub.backend.security.keycloak.KeycloakContext;
import com.vigourhub.backend.service.AccountService;
import com.vigourhub.backend.service.NotificationService;
import com.vigourhub.backend.service.mapper.UserMapper;
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
    private final UserMapper mapper;
    private final AccountRepositoryAdapter accountAdapter;
    private KeycloakContext keycloakContext;
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final NotificationService notificationService;

    @Autowired
    public AccountServiceImpl(UserMapper mapper, AccountRepositoryAdapter accountAdapter, KeycloakContext keycloakContext, NotificationService notificationService) {
        this.mapper = mapper;
        this.accountAdapter = accountAdapter;
        this.keycloakContext = keycloakContext;
        this.notificationService = notificationService;
    }

    @Override
    @Transactional
    public AccountResponseDTO createAccount(AccountRequestDTO request) throws Exception {

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

        UserRequestDTO userRequest = request.getUser();

        try {
            var keycloakId = this.keycloakContext.createKeycloakUser(userRequest);

            User user = mapper.toUserDomain(userRequest);
            user.setId(UUID.randomUUID());
            user.setAccount(account);
            user.setKeycloakId(keycloakId);
            user.setEmailApproved(false);

            Optional<Role> instructorRole = accountAdapter.findRoleByName(ROLE_INSTRUCTOR);
            List<Role> roles = new ArrayList<>();

            if (instructorRole.isEmpty()) {
                throw new InternalServerError("Role is not present");
            }

            roles.add(instructorRole.get());
            user.setRoles(roles);
            accountAdapter.insert(user);

            UserResponseDTO dto = mapper.toUserResponseDTO(user);
            notificationService.sendAccountCreatedNotification(dto);

            return mapper.toAccountDTO(account, user);
        }catch (Exception ex) {
            throw new InternalServerError(ex.getMessage());
        }
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
    public IdResponseDto registerInvitedClient(String token, UserRequestDTO request) throws Exception {

        Optional<ClientInvitation> invitation = this.accountAdapter.findInvitationById(token);

        if (invitation.isEmpty()) {
            throw new NotFoundException("Invitation not found");
        }

        var _invitation = invitation.get();

        User client = new User();
        client.setUsername(_invitation.getUsername());
        client.setId(UUID.randomUUID());
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setEmailApproved(false);

        UUID keycloakId;
        try {
            keycloakId = keycloakContext.createKeycloakUser(request);
        }catch (Exception ex) {
            logger.info(ex.getMessage());
            throw new InternalServerError(ex.getMessage());
        }

        client.setKeycloakId(keycloakId);

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
    @Override
    public void approveRegistration(String token) throws Exception {

    }
}
