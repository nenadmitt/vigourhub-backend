package com.vigourhub.backend.web.controllers;

import com.vigourhub.backend.infrastructure.exceptions.ConflictException;
import com.vigourhub.backend.infrastructure.security.SecurityAuthentication;
import com.vigourhub.backend.infrastructure.security.SecurityUserDetails;
import com.vigourhub.backend.service.AccountService;
import com.vigourhub.backend.web.WebConstants;
import com.vigourhub.backend.dto.accounts.AccountRequestDto;
import com.vigourhub.backend.dto.accounts.AccountResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(WebConstants.V1 + WebConstants.ACCOUNTS)
public class AccountController {
    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponseDto> createAccount(@Valid @RequestBody AccountRequestDto request) throws Exception {
        return ResponseEntity.ok(accountService.createAccount(request));
    }
}
