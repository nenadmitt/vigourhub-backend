package com.vigourhub.backend.security.keycloak;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.vigourhub.backend.infrastructure.properties.KeycloakProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
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
            var pk = (RSAPublicKey) generatePublicKeyFromString(this.properties.getPubkey());
            var algo = Algorithm.RSA256(pk);
            this.verifier = JWT.require(algo).build();
            logger.info("Setting up keycloak token verifier!");
        }
        catch(Exception e){
            logger.info("Error loading keycloak public key " + e.getMessage());
        }
    }

    private PublicKey generatePublicKeyFromString(String pk){
        try {
            var decoded = Base64.getDecoder().decode(pk);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec( decoded);
            return factory.generatePublic(encodedKeySpec);
        }catch (Exception e) {
            logger.info("Error while generating private key");
        }
        return null;
    }

    public String verifyToken(String token) throws JWTVerificationException {

        try {
            DecodedJWT jwt = this.verifier.verify(token);
            var claims = jwt.getClaims();
            return claims.get("preferred_username").asString();
        }catch(JWTVerificationException ex) {
            logger.info("Error occurred while verifying the token integrity " + ex.getMessage());
           throw new JWTVerificationException("Token is invalid");
        }
    }
}
