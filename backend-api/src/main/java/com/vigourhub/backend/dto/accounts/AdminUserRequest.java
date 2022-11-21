package com.vigourhub.backend.dto.accounts;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AdminUserRequest {
    @NotBlank
    @Email
    private String username;
    @NotBlank
    @Size(min=6, max=55)
    private String password;
    @NotBlank
    @Size(min=2, max=55)
    private String firstName;
    @NotBlank
    @Size(min=2, max=55)
    private String lastName;
    private String language;
}
