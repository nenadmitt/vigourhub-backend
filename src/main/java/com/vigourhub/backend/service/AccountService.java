package com.vigourhub.backend.service;

import com.vigourhub.backend.infrastructure.exceptions.ConflictException;
import com.vigourhub.backend.dto.accounts.AccountRequestDto;
import com.vigourhub.backend.dto.accounts.AccountResponseDto;

public interface AccountService {
    AccountResponseDto createAccount(AccountRequestDto request) throws ConflictException;
}
