package com.vigourhub.backend.infrastructure.security.keycloak;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.vigourhub.backend.infrastructure.properties.KeycloakProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Logger;

@Component
@Service
public class KeycloakTokenVerifier {

    private KeycloakProperties properties;
    private JWTVerifier verifier;
    private final Logger logger = Logger.getLogger("KeycloakTokenVerifier");

    @Autowired
    public KeycloakTokenVerifier(KeycloakProperties properties) {
        this.properties = properties;
        this.init();
    }

    private void init() {
        try{
            byte[] byteKey = Base64.decode(this.properties.getPubkey().getBytes());
            X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            var pk = (RSAPublicKey) kf.generatePublic(X509publicKey);

            var algo = Algorithm.RSA256(pk);
            this.verifier = JWT.require(algo).build();
            logger.info("Setting up keycloak token verifier!");
        }
        catch(Exception e){
            logger.info("Error loading keycloak public key " + e.getMessage());
//            System.exit(-1);
        }
    }

    public boolean verifyToken(String token) {

        try {
            this.verifier.verify(token);
            return true;
        }catch(Exception e) {
            logger.info("InValid TOKEN");
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }
}
