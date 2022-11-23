package com.vigourhub.backend.infrastructure.publisher;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationPublisher {

    RabbitTemplate template;
    Queue queue;

    @Autowired
    public NotificationPublisher(RabbitTemplate template) {
        this.template = template;
        this.queue = new Queue("notification-queue",false);
    }

    public void send(TestDto message) {
        template.convertAndSend(this.queue.getName(), message);
    }
}
