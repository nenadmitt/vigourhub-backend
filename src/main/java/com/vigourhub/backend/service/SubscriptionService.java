package com.vigourhub.backend.service;

import com.vigourhub.backend.web.controllers.accounts.dto.SubscriptionDto;

import java.util.List;

public interface SubscriptionService {
    List<SubscriptionDto> getAllSubscriptions();
}
