package com.vigourhub.backend.publishers.impl;

import com.vigourhub.backend.dto.events.UserInfoDto;
import com.vigourhub.backend.publishers.NotificationPublisher;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationPublisherImpl implements NotificationPublisher {

    RabbitTemplate template;

    @Autowired
    public NotificationPublisherImpl(RabbitTemplate template) {
        this.template = template;
    }

    public void send(String message) {
        UserInfoDto dto = new UserInfoDto();
        dto.setId(UUID.randomUUID().toString());
        dto.setEmail("nenadmitt@gmail.com");
        template.convertAndSend("account.created", dto);
    }

}
