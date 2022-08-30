package com.vigourhub.backend.service;

import com.vigourhub.backend.web.controllers.accounts.dto.AccountRequestDto;
import com.vigourhub.backend.web.controllers.accounts.dto.AccountResponseDto;
import org.springframework.stereotype.Service;

public interface AccountService {
    AccountResponseDto createAccount(AccountRequestDto request);
}
