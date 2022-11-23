package com.vigourhub.backend.dto.accounts;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionDto {
    private String id;
    private String name;
    private Float price;
    private Integer maxUsers;
}
