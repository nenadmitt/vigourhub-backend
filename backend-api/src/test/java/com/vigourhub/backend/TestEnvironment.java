package com.vigourhub.backend;

import java.util.List;

public class TestEnvironment {

    private static final List<String> envs = List.of(
            "POSTGRES_DATABASE=vigourhub-test",
            "POSTGRES_HOST=localhost:5432",
            "POSTGRES_PASSWORD=vhadminpassword",
            "POSTGRES_USER=vhadmin",
            "KEYCLOAK_URL=http://localhost:8080/auth",
            "KEYCLOAK_PUBLIC_KEY=MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0QNEHEHKeLFJEnRQyrP7TR48j4iiVRLJ9EJeJYLnk2Pci/jNXwUmqy16Eldyww7jsQjw0BIBd4GlEI5SRlXD1yge7i2JztCubo2RnEqwjebSTUQmt32e7SGXH2+QYhZ7PZl8Qk4/TIfkb3yZj/mMjXKrAjj7328MQ5k3fzDB+VEad1AKFMSSShFegNBsXW/LvGM0Tuv/cCK7MVnvWs9vSx2TXHTNr2qZSblZqS8m2UXxveRvuNqYszcZNSq8AwBdR0jHPv3Kueoart3Z4AZRDn3TdHyX8LtPvxmGbcHiM8jjhBs0ON6TP3mWOloueXcUtoXscGHzBLkpDRx1ae0EJwIDAQAB",
            "KEYCLOAK_SERVICE_ACCOUNT=backend-api-service-account",
            "KEYCLOAK_SERVICE_ACCOUNT_SECRET=yfbh4KcNKgJMcfh7pZrFchYbunNGf0MN",
            "RBMQ_USER=rbmquser",
            "RBMQ_PASSWORD=rbmqpass",
            "SECURITY_PRIVATE_KEY=12"
    );

    public static void setupEnv() {
        envs.forEach(e -> {
            var split = e.split("=");
            System.setProperty(split[0], split[1]);
        });
    }
}
