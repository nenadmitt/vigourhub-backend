package com.vigourhub.backend.infrastructure.publisher;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestPublisher {

    private final RabbitTemplate template;
    private final Queue queue;

    @Autowired
    public TestPublisher(RabbitTemplate template, Queue queue) {
        this.template = template;
        this.queue = queue;
    }

    public void send(String message) {
        System.out.println(this.queue.getName() + " queue name");
        template.convertAndSend(this.queue.getName(), message);
    }
}
