package com.vigourhub.backend.web.controllers.accounts;

import com.vigourhub.backend.service.SubscriptionService;
import com.vigourhub.backend.web.WebConstants;
import com.vigourhub.backend.web.controllers.accounts.dto.SubscriptionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(WebConstants.V1 + WebConstants.SUBSCRIPTIONS)
public class SubscriptionsController {

    private SubscriptionService service;

    @Autowired
    public SubscriptionsController(SubscriptionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionDto>> getSubscription() {
        return ResponseEntity.ok(service.getAllSubscriptions());
    }
}
