package com.vigourhub.backend.service.impl;

import com.vigourhub.backend.dto.accounts.UserResponseDTO;
import com.vigourhub.backend.publishers.impl.NotificationPublisherImpl;
import com.vigourhub.backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationServiceImpl implements NotificationService {

    private NotificationPublisherImpl publisher;

    @Autowired
    public NotificationServiceImpl(NotificationPublisherImpl publisher) {
        this.publisher = publisher;
    }

    @Override
    public void sendAccountCreatedNotification(UserResponseDTO accountOwner) {

    }
}
