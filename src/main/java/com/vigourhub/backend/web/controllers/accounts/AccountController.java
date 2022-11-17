package com.vigourhub.backend.web.controllers.accounts;

import com.vigourhub.backend.infrastructure.exceptions.ConflictException;
import com.vigourhub.backend.infrastructure.security.SecurityAuthentication;
import com.vigourhub.backend.service.AccountService;
import com.vigourhub.backend.web.WebConstants;
import com.vigourhub.backend.dto.accounts.AccountRequestDto;
import com.vigourhub.backend.dto.accounts.AccountResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AccountResponseDto> createAccount(@Valid @RequestBody AccountRequestDto request) throws ConflictException {
        return ResponseEntity.ok(accountService.createAccount(request));
    }

    @GetMapping("/test")
    public ResponseEntity<String> testFilter() {

        var context = SecurityContextHolder.getContext();
        SecurityAuthentication auth = (SecurityAuthentication) context.getAuthentication();
        System.out.println(auth.getName());
        System.out.println(auth.getDetails().toString());
        auth.getAuthorities().stream().forEach(a -> System.out.println(a.toString()));
        return ResponseEntity.ok("Hello There!");
    }
}
