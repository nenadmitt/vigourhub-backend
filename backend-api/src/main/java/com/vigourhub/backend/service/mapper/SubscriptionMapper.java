package com.vigourhub.backend.service.mapper;

import com.vigourhub.backend.domain.models.Subscription;
import com.vigourhub.backend.dto.accounts.SubscriptionDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
    SubscriptionDto toDto(Subscription subscription);
    List<SubscriptionDto> toDtos(List<Subscription> subscriptions);
}
