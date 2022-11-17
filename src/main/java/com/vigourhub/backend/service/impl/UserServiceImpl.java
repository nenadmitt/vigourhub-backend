package com.vigourhub.backend.service.impl;

import com.vigourhub.backend.domain.account.Role;
import com.vigourhub.backend.dto.users.UserDto;
import com.vigourhub.backend.infrastructure.exceptions.NotFoundException;
import com.vigourhub.backend.repository.UserRepository;
import com.vigourhub.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto getByUsername(String username) throws NotFoundException {

        var optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()){
            throw new NotFoundException("User with id, not found");
        }

        var user = optionalUser.get();
        var dto = new UserDto();
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setId(user.getId().toString());
        dto.setAccountId(user.getAccount().getId().toString());
        dto.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));

        return dto;
    }
}

