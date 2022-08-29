package com.vigourhub.backend.infrastructure.security.keycloak;

import com.vigourhub.backend.infrastructure.properties.KeycloakProperties;
import com.vigourhub.backend.infrastructure.security.keycloak.dtos.AuthRequestDto;
import com.vigourhub.backend.infrastructure.security.keycloak.dtos.AuthResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class KeycloakServiceImpl {

    private final String GRANT_CLIENT_CREDENTIALS = "client_credentials";
    private RestTemplate http;
    private KeycloakProperties properties;

    private String token;
    @Autowired
    public KeycloakServiceImpl(RestTemplateBuilder builder, KeycloakProperties properties) {
        this.http = builder.build();
        this.properties = properties;

        var authEndpoint = properties.getUrl() + properties.getTokenEndpoint();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("client_id", properties.getClient());
        map.add("client_secret", properties.getSecret());
        map.add("grant_type", GRANT_CLIENT_CREDENTIALS);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        try {
            var authResponse = this.http.postForEntity(authEndpoint, request, AuthResponseDto.class);
            System.out.println(authResponse.getBody());
            this.token = authResponse.getBody().getAccessToken();
            this.createUser();
        }catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void createUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", String.format("Bearer %s", this.token));

        Map<String,String> map = new HashMap<>();
        map.put("username","nenadmit@outlook.com");
        map.put("email","nenadmit@outlook.com");
        map.put("credentials", "[{\"type\":\"password\", \"value\": \"dreadw4r\", \"temporary\":false}]");

        HttpEntity<Map<String,String>> request = new HttpEntity<>(map, headers);

        var endpoint = this.properties.getUrl() + this.properties.getUsersEndpoint();
        var authResponse = this.http.postForEntity(endpoint, request, HashMap.class);
        System.out.println(authResponse.getBody());

    }


}
