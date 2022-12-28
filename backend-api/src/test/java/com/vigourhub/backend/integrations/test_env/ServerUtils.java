package com.vigourhub.backend.integrations.test_env;

public class ServerUtils {

    public static String serverURL(int port, String endpoint) {
        return String.format("http://localhost:%s%s", port, endpoint);
    }
}
