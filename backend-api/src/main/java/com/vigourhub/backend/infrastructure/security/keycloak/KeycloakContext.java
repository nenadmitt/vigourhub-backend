package com.vigourhub.backend.infrastructure.security.keycloak;

import com.vigourhub.backend.dto.accounts.AdminUserRequest;
import com.vigourhub.backend.infrastructure.exceptions.InternalServerError;
import com.vigourhub.backend.infrastructure.properties.KeycloakProperties;
import com.vigourhub.backend.dto.keycloak.KeycloakUser;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import javax.naming.AuthenticationException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class KeycloakContext {
    Logger logger = Logger.getLogger(this.getClass().getName());
    KeycloakProperties properties;
    Keycloak keycloak;

    @Autowired
    public KeycloakContext(KeycloakProperties properties, Keycloak keycloak) {
        this.properties = properties;
        this.keycloak = keycloak;
    }


    public UUID createKeycloakUser(AdminUserRequest user) throws Exception {

        try {

            UserRepresentation representation = new UserRepresentation();
            representation.setUsername(user.getUsername());
            representation.setEmail(user.getUsername());
            representation.setFirstName(user.getFirstName());
            representation.setLastName(user.getLastName());
            representation.setEnabled(false);
            representation.setEmailVerified(false);

            CredentialRepresentation credentials = new CredentialRepresentation();
            credentials.setType(CredentialRepresentation.PASSWORD);
            credentials.setTemporary(false);
            credentials.setValue(user.getPassword());

            representation.setCredentials(List.of(credentials));
            var resp = keycloak.realm("vigourhub").users().create(representation);
            System.out.println(resp.getHeaders());
            return UUID.randomUUID();
        }catch (Exception ex) {
            throw new InternalServerError("Error creating keycloak user " + ex.getMessage());
        }
    }

    public void enableKeycloakUser(UUID userId) {

        var realm = keycloak.realm("vigourhub");
        var user = keycloak.realm("vigourhub").users().get(userId.toString());
        var representation = user.toRepresentation();
        representation.setEnabled(true);
        representation.setEmailVerified(true);
        user.update(representation);

    }

    public UUID createKeycloakUser(KeycloakUser user) throws AuthenticationException {

       return UUID.randomUUID();
    }
    private void initializeContext() {


    }

    private UUID getKeycloakIdFromHeaders(HttpHeaders headers) throws Exception {
        List<String> locationString = headers.get(HttpHeaders.LOCATION);
        var errorMessage = "Cannot get keycloak id from headers";

        if (locationString == null || locationString.isEmpty()) {
            throw new InternalServerError(errorMessage);
        }

        var locationUrl = locationString.get(0);
        var split = locationUrl.split("/");
        var userId = split[split.length - 1];

        UUID id;
        try {
            id = UUID.fromString(userId);
        }catch (IllegalArgumentException ex) {
            throw new InternalServerError(errorMessage);
        }
        return id;
    }
}
