package com.vigourhub.backend.dto.users;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {
    private String id;
    private String accountId;
    private String username;
    private String firstName;
    private String lastName;
    private List<String> roles;
}
