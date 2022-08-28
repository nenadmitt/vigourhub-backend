package com.vigourhub.backend.web.controllers.accounts.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class AccountResponseDto {
    private UUID id;
    private String name;
    private LocalDateTime trialStarted;
}
