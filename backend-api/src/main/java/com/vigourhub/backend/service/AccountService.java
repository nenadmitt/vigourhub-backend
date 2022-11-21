package com.vigourhub.backend.service;

import com.vigourhub.backend.dto.accounts.ClientInvitationDto;
import com.vigourhub.backend.dto.users.UserDto;
import com.vigourhub.backend.infrastructure.exceptions.ConflictException;
import com.vigourhub.backend.dto.accounts.AccountRequestDto;
import com.vigourhub.backend.dto.accounts.AccountResponseDto;
import com.vigourhub.backend.infrastructure.exceptions.InternalServerError;

public interface AccountService {
    AccountResponseDto createAccount(AccountRequestDto request) throws Exception;
    ClientInvitationDto generateClientInvitation(String username) throws Exception;

    String registerInvitedClient(String token, UserDto userDto) throws Exception;
}
