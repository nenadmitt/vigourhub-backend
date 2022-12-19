package com.vigourhub.backend.service;

import com.vigourhub.backend.dto.accounts.UserResponseDTO;

public interface NotificationService {

    void sendAccountCreatedNotification(UserResponseDTO accountOwner);
}
