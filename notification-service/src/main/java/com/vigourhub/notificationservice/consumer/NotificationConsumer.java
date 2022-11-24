package com.vigourhub.notificationservice.consumer;

import com.vigourhub.notificationservice.dto.NotificationQueues;
import com.vigourhub.notificationservice.dto.UserInfoDto;
import com.vigourhub.notificationservice.mail_sender.MailSender;
import com.vigourhub.notificationservice.service.NotificationService;
import org.springframework.amqp.core.Queue;
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
    private void accountCreatedListener(@Payload UserInfoDto userInfo) {
        log.info("Received account.created message " + userInfo);
        service.handle(NotificationQueues.AccountCreated, userInfo);
    }

    @RabbitListener(queues = {"user.invited"})
    private void userInvitedListener(@Payload UserInfoDto userInfo) {
        service.handle(NotificationQueues.UserInvited, userInfo);
    }
}
