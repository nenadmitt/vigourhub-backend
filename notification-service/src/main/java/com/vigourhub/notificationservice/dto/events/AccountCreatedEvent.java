package com.vigourhub.notificationservice.dto.events;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountCreatedEvent implements Serializable {
    private String accountId;
    private String accountName;
    private String activationCode;
    private String ownerEmail;
    private String ownerName;
}
