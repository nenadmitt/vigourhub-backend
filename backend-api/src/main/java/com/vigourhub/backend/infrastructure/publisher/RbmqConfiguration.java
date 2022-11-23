package com.vigourhub.backend.infrastructure.publisher;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@EnableRabbit
@Configuration
public class RbmqConfiguration {

    @Value("nenadmit")
    private String message;

    @Bean
    public Queue queue(){
        return new Queue(message, true);
    }

}
