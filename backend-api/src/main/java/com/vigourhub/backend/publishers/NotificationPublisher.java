package com.vigourhub.backend.publishers;

import com.vigourhub.backend.dto.events.AccountCreatedEvent;
import com.vigourhub.backend.dto.events.UserInvitedEvent;

public interface NotificationPublisher {

    void publishAccountCreated(AccountCreatedEvent event);

    void publishUserInvited(UserInvitedEvent event);
}
