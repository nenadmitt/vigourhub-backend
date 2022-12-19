package com.vigourhub.backend.service;

import com.vigourhub.backend.dto.accounts.UserResponseDTO;
import com.vigourhub.backend.infrastructure.exceptions.NotFoundException;

public interface UserService {
    UserResponseDTO getByUsername(String username) throws NotFoundException;
}
