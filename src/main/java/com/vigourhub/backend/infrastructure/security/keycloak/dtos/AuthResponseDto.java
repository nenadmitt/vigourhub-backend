package com.vigourhub.backend.infrastructure.security.keycloak.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class AuthResponseDto implements Serializable {
    @JsonProperty("access_token")
    private String accessToken;
    private String refreshToken;
}
