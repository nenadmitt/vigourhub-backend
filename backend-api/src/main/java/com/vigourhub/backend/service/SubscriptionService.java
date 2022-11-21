package com.vigourhub.backend.service;

import com.vigourhub.backend.dto.accounts.SubscriptionDto;

import java.util.List;

public interface SubscriptionService {
    List<SubscriptionDto> getAllSubscriptions();
}
