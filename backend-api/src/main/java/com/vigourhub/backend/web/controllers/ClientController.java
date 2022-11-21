package com.vigourhub.backend.web.controllers;

import com.vigourhub.backend.dto.users.UserDto;
import com.vigourhub.backend.service.AccountService;
import com.vigourhub.backend.web.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController(WebConstants.V1 + WebConstants.CLIENTS)
public class ClientController {

    private AccountService service;

    @Autowired
    public ClientController(AccountService service) {
        this.service = service;
    }

    @PostMapping("/invite")
    public ResponseEntity<Void> inviteClient(@RequestParam String username) throws Exception {
        service.generateClientInvitation(username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{token}")
    public ResponseEntity<String> registerClient(@PathVariable UUID invitationToken, @RequestBody UserDto request) throws Exception {
        return ResponseEntity.ok(service.registerInvitedClient(invitationToken.toString(), request));
    }
}
