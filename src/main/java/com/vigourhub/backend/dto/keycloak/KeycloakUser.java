package com.vigourhub.backend.dto.keycloak;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class KeycloakUser {
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("username")
    private String username;
    @JsonIgnore
    private List<KeycloakCredentials> credentials;

    @JsonProperty("credentials")
    private KeycloakCredentials[] _credentials;

    @JsonProperty("enabled")
    private final boolean enabled = true;

    public KeycloakUser(String firstName, String lastName, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    public void setCredentials(String password){
        List<KeycloakCredentials> credentials = new ArrayList<>(1);
        credentials.add(new KeycloakCredentials(password));
        this._credentials = new KeycloakCredentials[1];
        this._credentials[0] = new KeycloakCredentials(password);
    }
}
