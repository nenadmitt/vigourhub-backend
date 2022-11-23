package com.vigourhub.notificationservice.consumer;

import com.vigourhub.notificationservice.mail_sender.MailSender;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {
    private Queue queue;
    private MailSender mailSender;
    private final String NAME = "notification-queue";

    public NotificationConsumer() {
        this.queue = new Queue(NAME, false);
    }

    @RabbitListener(queues = {NAME})
    private void testing(@Payload TestDto test) {
        System.out.println("Payload received " + test);
    }
}
