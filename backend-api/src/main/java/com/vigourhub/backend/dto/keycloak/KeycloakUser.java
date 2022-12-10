package com.vigourhub.backend.dto.keycloak;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private final boolean enabled = false;

    public KeycloakUser(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        setCredentials(password);
    }

    private void setCredentials(String password){
        List<KeycloakCredentials> credentials = new ArrayList<>(1);
        credentials.add(new KeycloakCredentials(password));
        this._credentials = new KeycloakCredentials[1];
        this._credentials[0] = new KeycloakCredentials(password);
    }
}
