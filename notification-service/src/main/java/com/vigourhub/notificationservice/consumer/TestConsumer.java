package com.vigourhub.notificationservice.consumer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class TestConsumer {

    @RabbitListener(queues = {"nenadmit"})
    public void receive(@Payload String body) {
        System.out.println("Message " + body);
    }
}
