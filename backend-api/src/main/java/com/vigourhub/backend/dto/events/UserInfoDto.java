package com.vigourhub.backend.dto.events;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoDto implements Serializable {

    private String id;
    private String accountId;
    private String accountName;
    private String firstName;
    private String lastName;
    private String email;
}