package com.vigourhub.backend.publishers.impl;

import com.vigourhub.backend.dto.events.AccountCreatedEvent;
import com.vigourhub.backend.dto.events.UserInvitedEvent;
import com.vigourhub.backend.publishers.NotificationPublisher;
import com.vigourhub.backend.publishers.NotificationQueues;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationPublisherImpl implements NotificationPublisher {

    RabbitTemplate template;

    @Autowired
    public NotificationPublisherImpl(RabbitTemplate template) {
        this.template = template;
    }

    @Override
    public void publishAccountCreated(AccountCreatedEvent event) {
        template.convertAndSend(NotificationQueues.AccountCreated.value(), event);
    }
    @Override
    public void publishUserInvited(UserInvitedEvent event) {
        template.convertAndSend(NotificationQueues.UserInvited.value(), event);
    }
}
