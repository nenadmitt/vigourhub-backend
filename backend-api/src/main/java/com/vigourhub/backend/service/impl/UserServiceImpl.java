package com.vigourhub.backend.service.impl;

import com.vigourhub.backend.domain.adapters.AccountRepositoryAdapter;
import com.vigourhub.backend.domain.entity.account.Role;
import com.vigourhub.backend.domain.entity.account.User;
import com.vigourhub.backend.dto.accounts.UserResponseDTO;
import com.vigourhub.backend.infrastructure.exceptions.NotFoundException;
import com.vigourhub.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private AccountRepositoryAdapter accountAdapter;

    @Autowired
    public UserServiceImpl(AccountRepositoryAdapter accountAdapter) {
        this.accountAdapter = accountAdapter;
    }

    @Override
    public UserResponseDTO getByUsername(String username) throws NotFoundException {

        var optionalUser = accountAdapter.findUserByUsername(username);

        if (optionalUser.isEmpty()){
            throw new NotFoundException("User with id, not found");
        }

        var user = optionalUser.get();

        return toDto(user);
    }

    public UserResponseDTO toDto(User user) {
        var dto = new UserResponseDTO();
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setId(user.getId().toString());
        dto.setAccountId(user.getAccount().getId().toString());
        dto.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));

        return dto;
    }
}

