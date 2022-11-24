package com.vigourhub.notificationservice.service;

import com.vigourhub.notificationservice.dto.NotificationEvent;
import com.vigourhub.notificationservice.dto.NotificationQueues;
import com.vigourhub.notificationservice.dto.events.AccountCreatedEvent;
import com.vigourhub.notificationservice.dto.events.UserInvitedEvent;

public interface NotificationService {

    void handleUserInvited(UserInvitedEvent event);
    void handleAccountCreated(AccountCreatedEvent event);
}
