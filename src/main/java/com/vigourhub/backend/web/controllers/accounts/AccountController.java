package com.vigourhub.backend.web.controllers.accounts;

import com.vigourhub.backend.service.AccountService;
import com.vigourhub.backend.web.WebConstants;
import com.vigourhub.backend.web.controllers.accounts.dto.AccountRequestDto;
import com.vigourhub.backend.web.controllers.accounts.dto.AccountResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AccountResponseDto> createAccount(@Valid @RequestBody AccountRequestDto request) {
        return ResponseEntity.ok(accountService.createAccount(request));
    }
}
