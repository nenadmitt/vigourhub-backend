package com.vigourhub.backend.integrations.accounts;

import com.vigourhub.backend.dto.accounts.AccountRequestDTO;
import com.vigourhub.backend.dto.accounts.AccountResponseDTO;
import com.vigourhub.backend.infrastructure.exceptions.ApiError;
import com.vigourhub.backend.integrations.test_env.AuthServerMock;
import com.vigourhub.backend.integrations.test_env.ServerUtils;
import com.vigourhub.backend.integrations.test_env.TestEnvironment;
import com.vigourhub.backend.VigourhubBackendApiApplication;
import com.vigourhub.backend.security.keycloak.KeycloakContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@SpringBootTest(classes = VigourhubBackendApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient(registerRestTemplate = true)
public class AccountIntegrations {

    @LocalServerPort
    private int port;
    @Autowired
    private RestTemplate restTemplate;

    @Mock
    KeycloakContext keycloakContext;

    @BeforeAll
    static void setup() {
        TestEnvironment.setupEnv();
        AuthServerMock.setup();
    }

    @Test
    void testCreateAccount_OK() {

        var random = UUID.randomUUID().toString();
        var accountURL = ServerUtils.serverURL(port,"/api/v1/accounts");

        var accountRequestDTO = new AccountRequestDTO();
        var userRequestDTO = AccountUtils.userRequestDTO();

        accountRequestDTO.setName("account-" + random);
        accountRequestDTO.setUser(userRequestDTO);

        HttpEntity<AccountRequestDTO> request = new HttpEntity<>(accountRequestDTO);
        var response = restTemplate.postForEntity(accountURL, request, AccountResponseDTO.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        var responseDTO = response.getBody();

        Assertions.assertEquals(responseDTO.getName(), accountRequestDTO.getName());
        var userResponseDTO = responseDTO.getOwner();
        Assertions.assertEquals(userResponseDTO.getFirstName(), userRequestDTO.getFirstName());
        Assertions.assertEquals(userResponseDTO.getLastName(), userRequestDTO.getLastName());
        Assertions.assertEquals(userResponseDTO.getUsername(), userRequestDTO.getUsername());
    }

    @Test
    void testCreateAccount_BadRequest() {

        var accountURL = ServerUtils.serverURL(port,"/api/v1/accounts");

        var accountRequestDTO = new AccountRequestDTO();
        var userRequestDTO = AccountUtils.userRequestDTO();
        accountRequestDTO.setUser(userRequestDTO);

        accountRequestDTO.setName("");

        HttpEntity<AccountRequestDTO> request = new HttpEntity<>(accountRequestDTO);

        try {
            var response = restTemplate.postForEntity(accountURL, request, ApiError.class);
        }catch(Exception ex) {

            System.out.println(ex.getMessage());
        }
    }
}
