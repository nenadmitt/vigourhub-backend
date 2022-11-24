package com.vigourhub.notificationservice.consumer;

import com.vigourhub.notificationservice.dto.NotificationEvent;
import com.vigourhub.notificationservice.dto.NotificationQueues;
import com.vigourhub.notificationservice.dto.events.AccountCreatedEvent;
import com.vigourhub.notificationservice.dto.events.UserInvitedEvent;
import com.vigourhub.notificationservice.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class NotificationConsumer {

    private final NotificationService service;
    private final Logger log = Logger.getLogger(this.getClass().getName());

    @Autowired
    public NotificationConsumer(NotificationService service) {
        this.service = service;
    }

    @RabbitListener(queues = "account.created")
    private void accountCreatedListener(@Payload AccountCreatedEvent event) {
        log.info("Received account.created message " + event);
        service.handleAccountCreated(event);
    }

    @RabbitListener(queues = {"user.invited"})
    private void userInvitedListener(@Payload UserInvitedEvent event) {
        service.handleUserInvited(event);
    }
}
