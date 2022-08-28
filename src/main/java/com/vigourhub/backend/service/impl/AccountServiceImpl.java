package com.vigourhub.backend.service.impl;

import com.vigourhub.backend.domain.account.Account;
import com.vigourhub.backend.domain.account.Role;
import com.vigourhub.backend.domain.account.User;
import com.vigourhub.backend.repository.AccountRepository;
import com.vigourhub.backend.repository.RoleRepository;
import com.vigourhub.backend.repository.UserRepository;
import com.vigourhub.backend.service.AccountService;
import com.vigourhub.backend.web.controllers.accounts.dto.AccountRequestDto;
import com.vigourhub.backend.web.controllers.accounts.dto.AccountResponseDto;
import com.vigourhub.backend.web.controllers.accounts.dto.AdminUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private final String ROLE_INSTRUCTOR = "Instructor";
    private final String ROLE_CLIENT = "Client";
    private AccountRepository accountRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    @Transactional
    public AccountResponseDto createAccount(AccountRequestDto request) {

        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setName(request.getName());
        account.setTrial(true);
        account.setTrialStarted(LocalDateTime.now());
        account.setCreatedAt(LocalDateTime.now());

        accountRepository.save(account);

        AdminUserRequest userRequest = request.getUser();
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(userRequest.getUsername());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setAccount(account);

        Optional<Role> instructorRole = roleRepository.findByName(ROLE_INSTRUCTOR);
        List<Role> roles = new ArrayList<>();
        roles.add(instructorRole.get());

        user.setRoles(roles);

        userRepository.save(user);

        return null;
    }
}
