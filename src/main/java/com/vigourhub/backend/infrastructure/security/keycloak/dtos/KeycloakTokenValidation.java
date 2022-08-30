package com.vigourhub.backend.infrastructure.security.keycloak.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KeycloakTokenValidation {
    private boolean isValid;
    @JsonProperty("preferred_username")
    private String username;
}
