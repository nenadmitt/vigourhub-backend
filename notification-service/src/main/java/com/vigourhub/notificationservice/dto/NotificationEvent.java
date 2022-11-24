package com.vigourhub.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent implements Serializable {
    private String id;
    private String accountId;
    private String accountName;
    private String firstName;
    private String lastName;
    private String email;
}
