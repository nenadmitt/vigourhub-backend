package com.vigourhub.backend.web.controllers.accounts.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AccountRequestDto {
    @NotBlank(message = "Account name cannot be blank")
    @NotNull(message = "Account name cannot be null")
    @Size(min=2, max=55, message = "Account name must be between 2 and 55 characters")
    private String name;
    @NotNull
    private AdminUserRequest user;
}
