package com.vigourhub.backend.infrastructure.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {

    private String url;
    private String client;
    private String secret;
    private String tokenEndpoint;
    private String usersEndpoint;
}
