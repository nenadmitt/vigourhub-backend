package com.vigourhub.backend.security.keycloak;

import com.vigourhub.backend.dto.accounts.UserRequestDTO;
import com.vigourhub.backend.infrastructure.exceptions.InternalServerError;
import com.vigourhub.backend.infrastructure.properties.KeycloakProperties;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
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


    public UUID createKeycloakUser(UserRequestDTO user) throws Exception {

        try {

            UserRepresentation representation = new UserRepresentation();
            representation.setUsername(user.getUsername());
            representation.setEmail(user.getUsername());
            representation.setFirstName(user.getFirstName());
            representation.setLastName(user.getLastName());
            representation.setEnabled(true);
            representation.setEmailVerified(true);

            CredentialRepresentation credentials = new CredentialRepresentation();
            credentials.setType(CredentialRepresentation.PASSWORD);
            credentials.setTemporary(false);
            credentials.setValue(user.getPassword());

            representation.setCredentials(List.of(credentials));
            var resp = keycloak.realm("vigourhub").users().create(representation);
            return getKeycloakIdFromLocationHeader(resp.getLocation().toString());
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
    private UUID getKeycloakIdFromLocationHeader(String locationHeader) throws Exception {

        var url = locationHeader.split(",")[0];
        var splitedUrl = url.split("/");
        try {
            return UUID.fromString(splitedUrl[splitedUrl.length - 1]);
        }catch (IllegalArgumentException ex) {
            throw new InternalServerError("Error ocurred while parsing keycloak id");
        }
    }
}
