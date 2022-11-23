package com.vigourhub.backend.web.controllers;

import com.vigourhub.backend.infrastructure.publisher.NotificationPublisher;
import com.vigourhub.backend.infrastructure.publisher.TestDto;
import com.vigourhub.backend.infrastructure.publisher.TestPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private final TestPublisher publisher;
    private final NotificationPublisher notificationPublisher;

    @Autowired
    public TestController(TestPublisher publisher, NotificationPublisher notificationPublisher) {
        this.publisher = publisher;
        this.notificationPublisher = notificationPublisher;
    }

    @GetMapping
    public void testing() {
        publisher.send("This is message here!");

        var dto = new TestDto("nenadmit","username-here");
        this.notificationPublisher.send(dto);
    }
}
