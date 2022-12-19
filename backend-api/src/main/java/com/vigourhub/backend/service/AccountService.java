package com.vigourhub.backend.service;

import com.vigourhub.backend.dto.IdResponseDto;
import com.vigourhub.backend.dto.accounts.*;

public interface AccountService {
    AccountResponseDTO createAccount(AccountRequestDTO request) throws Exception;
    ClientInvitationDto generateClientInvitation(String username) throws Exception;
    IdResponseDto registerInvitedClient(String token, UserRequestDTO userDto) throws Exception;

    void approveRegistration(String token) throws Exception;
}
