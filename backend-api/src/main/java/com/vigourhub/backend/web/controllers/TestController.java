package com.vigourhub.backend.web.controllers;

import com.vigourhub.backend.publishers.impl.NotificationPublisherImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private final NotificationPublisherImpl notificationPublisherImpl;

    @Autowired
    public TestController(NotificationPublisherImpl notificationPublisherImpl) {
        this.notificationPublisherImpl = notificationPublisherImpl;
    }

    @GetMapping
    public void testing() {
        this.notificationPublisherImpl.send("{\"username\":\"nenadmit\"}");
    }
}
