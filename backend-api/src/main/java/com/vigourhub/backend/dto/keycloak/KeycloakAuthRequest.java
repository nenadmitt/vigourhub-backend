package com.vigourhub.backend.dto.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.Serializable;
import java.util.HashMap;

@Setter
@ToString
public class KeycloakAuthRequest implements Serializable {
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("client_secret")
    private String clientSecret;

    @Setter(value = AccessLevel.NONE)
    @JsonProperty("grant_type")
    private String grantType = "client_credentials";

    public MultiValueMap<String,String> toMap() {
        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
        map.add("client_id", this.clientId);
        map.add("client_secret",this.clientSecret);
        map.add("grant_type", this.grantType);
        return map;
    }
}
