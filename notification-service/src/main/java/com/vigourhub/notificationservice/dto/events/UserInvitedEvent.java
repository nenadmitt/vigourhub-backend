package com.vigourhub.notificationservice.dto.events;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInvitedEvent implements Serializable {
    private String accountId;
    private String accountName;
    private String email;
    private String name;
    private String invitationCode;
}
