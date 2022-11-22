package com.vigourhub.backend.service.impl;

import com.vigourhub.backend.domain.models.account.Subscription;
import com.vigourhub.backend.domain.repository.SubscriptionRepository;
import com.vigourhub.backend.service.SubscriptionService;
import com.vigourhub.backend.service.mapper.SubscriptionMapper;
import com.vigourhub.backend.dto.accounts.SubscriptionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private SubscriptionRepository subscriptionRepository;
    private SubscriptionMapper mapper;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, SubscriptionMapper mapper) {
        this.subscriptionRepository = subscriptionRepository;
        this.mapper = mapper;
    }

    public List<SubscriptionDto> getAllSubscriptions(){
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        return mapper.toDtos(subscriptions);
    }
}
