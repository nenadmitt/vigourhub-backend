package com.vigourhub.backend.service;

import com.vigourhub.backend.dto.users.UserDto;
import com.vigourhub.backend.infrastructure.exceptions.NotFoundException;

public interface UserService {
    UserDto getByUsername(String username) throws NotFoundException;
}
