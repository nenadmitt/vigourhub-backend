package com.vigourhub.backend.web.controllers;

import com.vigourhub.backend.dto.IdResponseDto;
import com.vigourhub.backend.dto.accounts.ClientInvitationDto;
import com.vigourhub.backend.dto.accounts.ClientRegistrationRequestDto;
import com.vigourhub.backend.dto.accounts.UserRequestDTO;
import com.vigourhub.backend.service.AccountService;
import com.vigourhub.backend.web.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping((WebConstants.V1 + WebConstants.CLIENTS))
public class ClientController {

    private AccountService service;

    @Autowired
    public ClientController(AccountService service) {
        this.service = service;
    }

    @PostMapping("/invite")
    public ResponseEntity<ClientInvitationDto> inviteClient(@RequestParam String username) throws Exception {
        service.generateClientInvitation(username);
        return ResponseEntity.ok(service.generateClientInvitation(username));
    }

    @PostMapping("/register")
    public ResponseEntity<IdResponseDto> registerClient(@RequestParam String invitationToken, @RequestBody UserRequestDTO request) throws Exception {
        return ResponseEntity.ok(service.registerInvitedClient(invitationToken, request));
    }
}
