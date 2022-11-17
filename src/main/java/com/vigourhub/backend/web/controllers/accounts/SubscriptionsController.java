package com.vigourhub.backend.web.controllers.accounts;

import com.vigourhub.backend.infrastructure.security.SecurityAuthentication;
import com.vigourhub.backend.infrastructure.security.SecurityUserDetails;
import com.vigourhub.backend.service.SubscriptionService;
import com.vigourhub.backend.web.WebConstants;
import com.vigourhub.backend.dto.accounts.SubscriptionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

        var user = (SecurityAuthentication) SecurityContextHolder.getContext().getAuthentication();
        System.out.println(user);
        System.out.println(user.getCredentials());
        var principal = (SecurityUserDetails) user.getDetails();
        System.out.println(principal.getUsername());
        System.out.println("------------");

        return ResponseEntity.ok(service.getAllSubscriptions());
    }
}
