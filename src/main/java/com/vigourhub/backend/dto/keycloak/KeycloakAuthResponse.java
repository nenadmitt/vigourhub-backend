package com.vigourhub.backend.dto.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public class KeycloakAuthResponse implements Serializable {
    @JsonProperty("access_token")
    private String token;
}
