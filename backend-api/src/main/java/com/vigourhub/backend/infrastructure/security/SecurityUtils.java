package com.vigourhub.backend.infrastructure.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static SecurityUserDetails getCurrentPrincipal() {
        SecurityContext context = SecurityContextHolder.getContext();
        SecurityUserDetails details = (SecurityUserDetails) context.getAuthentication().getDetails();

        return details;
     }

}
