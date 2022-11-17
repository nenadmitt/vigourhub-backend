package com.vigourhub.backend.infrastructure.security.keycloak;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.vigourhub.backend.infrastructure.properties.KeycloakProperties;
import com.vigourhub.backend.dto.keycloak.KeycloakAuthRequest;
import com.vigourhub.backend.dto.keycloak.KeycloakAuthResponse;
import com.vigourhub.backend.dto.keycloak.KeycloakTokenValidation;
import com.vigourhub.backend.dto.keycloak.KeycloakUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import javax.naming.AuthenticationException;
import java.util.logging.Logger;

@Component
public class KeycloakContext {

    public final Logger logger = Logger.getLogger(this.getClass().getName());
    private RestTemplate http;
    private KeycloakProperties properties;
    private String accessToken;

    @Autowired
    public KeycloakContext(RestTemplateBuilder builder, KeycloakProperties properties) {
        this.http = builder.build();
        this.properties = properties;
        this.initializeContext();
        Logger.getAnonymousLogger().info("Initializing KeycloakContext");
    }
    public void createKeycloakUser(KeycloakUser user) throws AuthenticationException {

        var usersEndpoint = properties.getUrl() + properties.getUsersEndpoint();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(this.accessToken);

        HttpEntity<KeycloakUser> request = new HttpEntity(user, headers);
        try {
            var response = this.http.postForEntity(usersEndpoint, request, Void.class);
        }catch(Exception ex) {
            throw new AuthenticationException(ex.getMessage());
        }

    }
    private void initializeContext() {

        try {
            var tokenEndpoint = properties.getUrl() + properties.getTokenEndpoint();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            var auth = new KeycloakAuthRequest();
            auth.setClientId(properties.getClient());
            auth.setClientSecret(properties.getSecret());

            HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(auth.toMap(), headers);
            var response = this.http.postForEntity(tokenEndpoint, request, KeycloakAuthResponse.class);
            this.accessToken = response.getBody().getToken();
        }catch(Exception e) {
            logger.info(String.format("Error creating keycloak context: %s",e.getMessage()));
            System.exit(-1);
        }
    }
}
