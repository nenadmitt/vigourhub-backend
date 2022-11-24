package com.vigourhub.backend.infrastructure.configuration;

import com.vigourhub.backend.publishers.NotificationQueues;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;


@EnableRabbit
@Configuration
public class RabbitMQConfiguration {
    private final Logger log = Logger.getLogger(this.getClass().getName());
    private final CachingConnectionFactory factory;

    @Autowired
    public RabbitMQConfiguration(CachingConnectionFactory factory) {
        this.factory = factory;
    }

    @Bean
    public Jackson2JsonMessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(Jackson2JsonMessageConverter converter){
        RabbitTemplate template = new RabbitTemplate(factory);
        template.setMessageConverter(converter);
        return template;

    }

    @Bean
    Queue accountCreatedQueue() {
        log.info("Creating a queue " + NotificationQueues.AccountCreated.value());
        return new Queue(NotificationQueues.AccountCreated.value(), false);
    }

    @Bean
    Queue userInvitedQueue() {
        log.info("Creating a queue " + NotificationQueues.UserInvited.value());
        return new Queue(NotificationQueues.UserInvited.value(), false);
    }
}
