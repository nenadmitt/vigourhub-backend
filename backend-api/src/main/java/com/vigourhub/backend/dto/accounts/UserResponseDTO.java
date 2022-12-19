package com.vigourhub.backend.dto.accounts;

import com.vigourhub.backend.domain.entity.account.Role;
import com.vigourhub.backend.domain.entity.account.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserResponseDTO {
    private String id;
    private String accountId;
    private String accountName;
    private String username;
    private String firstName;
    private String lastName;
    private List<String> roles;
}
