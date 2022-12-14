package com.vigourhub.backend.security.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.vigourhub.backend.dto.accounts.UserResponseDTO;
import com.vigourhub.backend.infrastructure.exceptions.NotFoundException;
import com.vigourhub.backend.security.SecurityAuthentication;
import com.vigourhub.backend.security.SecurityUserDetails;
import com.vigourhub.backend.security.keycloak.KeycloakContext;
import com.vigourhub.backend.security.keycloak.KeycloakTokenVerifier;
import com.vigourhub.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final KeycloakTokenVerifier verifier;
    private final Logger log = Logger.getLogger(this.getClass().getName());
    private final Set<String> excluded = new HashSet<>();

    @Autowired
    public JwtFilter(KeycloakContext context, UserService userService, KeycloakTokenVerifier verifier) {
        this.userService = userService;
        this.verifier = verifier;
        excluded.add("/api/v1/accounts");
        excluded.add("/api/v1/clients/register");
        excluded.add("/test");

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return this.excluded.contains(request.getRequestURI());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            response.sendError(HttpStatus.NOT_FOUND.value(), "Invalid token");
            return;
        }
        var token = bearerToken.split(" ")[1];

        var subject = "";
        try {
            subject = verifier.verifyToken(token);
        }catch (JWTVerificationException ex) {
            response.sendError(HttpStatus.NOT_FOUND.value(), "Invalid token");
            return;
        }

        try {
            UserResponseDTO userResponseDto = userService.getByUsername(subject);
            SecurityUserDetails user = new SecurityUserDetails();
            user.setUserId(userResponseDto.getId());
            user.setAccountId(userResponseDto.getAccountId());
            user.setUsername(userResponseDto.getUsername());
            user.setRoles(userResponseDto.getRoles());

            SecurityAuthentication auth = new SecurityAuthentication(user);
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        } catch (NotFoundException ex) {
            log.info(String.format("User with username %s, not found", subject));
            response.sendError(HttpStatus.FORBIDDEN.value(), "Invalid token");
        }

    }
}

