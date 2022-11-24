package com.vigourhub.backend.service;

import com.vigourhub.backend.dto.users.UserDto;

public interface NotificationService {

    void sendAccountCreatedNotification(UserDto accountOwner);
}
