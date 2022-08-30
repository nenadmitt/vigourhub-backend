package com.vigourhub.backend.infrastructure.security.filters;

import com.vigourhub.backend.dto.users.UserDto;
import com.vigourhub.backend.infrastructure.exceptions.NotFoundException;
import com.vigourhub.backend.infrastructure.security.SecurityAuthentication;
import com.vigourhub.backend.infrastructure.security.SecurityUserDetails;
import com.vigourhub.backend.infrastructure.security.keycloak.KeycloakContext;
import com.vigourhub.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private KeycloakContext context;
    private UserService userService;

    @Autowired
    public JwtFilter(KeycloakContext context, UserService userService) {
        this.context = context;
        this.userService = userService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        List<String> excludeUrlPatterns = Arrays.asList("/api/v1/accounts");
        System.out.println(request.getRequestURI());
        return excludeUrlPatterns.contains(request.getRequestURI());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("filter hit!");
        var bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            throw new ServletException("Token not present");
        }

        var token = bearerToken.substring(7);
        var validation = context.validateToken(token);

        if (!validation.isValid()) {
            response.sendError(403);
            return;
        }
        try {
            UserDto userDto = userService.getByUsername(validation.getUsername());
            SecurityUserDetails user = new SecurityUserDetails();
            user.setUserId(userDto.getId());
            user.setAccountId(userDto.getAccountId());
            user.setUsername(userDto.getUsername());
            user.setRoles(userDto.getRoles());

            SecurityAuthentication auth = new SecurityAuthentication(user);
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        } catch (NotFoundException ex) {
            response.sendError(403);
        }

    }
}

