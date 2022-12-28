package com.vigourhub.backend.integrations.accounts;

import com.vigourhub.backend.dto.accounts.UserRequestDTO;

import java.util.UUID;

public class AccountUtils {

    public static UserRequestDTO userRequestDTO() {

        var random = UUID.randomUUID();
        var userRequestDTO = new UserRequestDTO();

        userRequestDTO.setFirstName("John");
        userRequestDTO.setLastName("Doe");
        userRequestDTO.setUsername("username-" + random + "@email.com");
        userRequestDTO.setPassword(random + "123$");
        return userRequestDTO;
    }
}
