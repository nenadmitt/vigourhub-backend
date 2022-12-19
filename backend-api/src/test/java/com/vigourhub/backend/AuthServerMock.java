package com.vigourhub.backend;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
public class AuthServerMock {

    public static void generateSigningKeys(){
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair pair = generator.generateKeyPair();

            var privateKey = Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded());
            var publicKey = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());

            System.setProperty("KEYCLOAK_PRIVATE_KEY",privateKey);
            System.setProperty("KEYCLOAK_PUBLIC_KEY", publicKey);
        }catch(Exception ignored) {

        }
    }

    public String generateToken(String username) {

        try {
            var pk = loadPkFromEnv();
            var pubkey = loadPublicKeyFromEnv();
            Algorithm algorithm = Algorithm.RSA256(pubkey, pk);

            return JWT.create()
                    .withClaim("sub",username)
                    .withClaim("preferred_username", username)
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1000000000))
                    .sign(algorithm);

        }catch(Exception ignored) {
        }
        return "";
    }

    private RSAPrivateKey loadPkFromEnv() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String privateKeyStr = System.getProperty("KEYCLOAK_PRIVATE_KEY");
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return  (RSAPrivateKey) kf.generatePrivate(keySpec);
    }

    private RSAPublicKey loadPublicKeyFromEnv() {
        String pubkeyStr  = System.getProperty("KEYCLOAK_PUBLIC_KEY");
        try {
            var decoded = Base64.getDecoder().decode(pubkeyStr);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec( decoded);
            return (RSAPublicKey) factory.generatePublic(encodedKeySpec);
        }catch (Exception ignored) {

        }
        return null;
    }
}
