package com.vigourhub.backend.infrastructure.security.keycloak.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class AuthRequestDto implements Serializable {
    private String clientId;
    private String clientSecret;
    private String grantType;
}
