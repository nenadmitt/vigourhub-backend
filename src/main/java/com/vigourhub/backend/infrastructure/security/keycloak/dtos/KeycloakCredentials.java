package com.vigourhub.backend.infrastructure.security.keycloak.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class KeycloakCredentials {
    @JsonProperty("value")
    @Setter
    private String password;
    @JsonProperty("temporary")
    private final boolean temporary = false;
    @JsonProperty("type")
    private final String type = "password";

    public KeycloakCredentials(String password) {
        this.password = password;
    }
}
