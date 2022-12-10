package com.vigourhub.backend.infrastructure.configuration;

import com.vigourhub.backend.infrastructure.properties.KeycloakProperties;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KeycloakConfiguration {

    KeycloakProperties properties;

    @Autowired
    public KeycloakConfiguration(KeycloakProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .realm(properties.getRealm())
                .clientId(properties.getClient())
                .clientSecret(properties.getSecret())
                .serverUrl(properties.getUrl())
                .build();
    }
}
