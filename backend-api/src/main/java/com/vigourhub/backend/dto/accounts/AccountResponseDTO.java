package com.vigourhub.backend.dto.accounts;

import com.vigourhub.backend.domain.entity.account.Account;
import com.vigourhub.backend.domain.entity.account.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class AccountResponseDTO {
    private UUID id;
    private String name;
    private LocalDateTime trialStarted;
    private UserResponseDTO owner;
}
