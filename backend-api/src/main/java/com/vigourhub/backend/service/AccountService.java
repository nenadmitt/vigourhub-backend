package com.vigourhub.backend.service;

import com.vigourhub.backend.dto.IdResponseDto;
import com.vigourhub.backend.dto.accounts.ClientInvitationDto;
import com.vigourhub.backend.dto.accounts.ClientRegistrationRequestDto;
import com.vigourhub.backend.dto.accounts.AccountRequestDto;
import com.vigourhub.backend.dto.accounts.AccountResponseDto;

public interface AccountService {
    AccountResponseDto createAccount(AccountRequestDto request) throws Exception;
    ClientInvitationDto generateClientInvitation(String username) throws Exception;
    IdResponseDto registerInvitedClient(String token, ClientRegistrationRequestDto userDto) throws Exception;

    void approveRegistration(String token) throws Exception;
}
