package com.vigourhub.backend.dto.accounts;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AccountRequestDTO {
    @NotBlank(message = "Account name cannot be blank")
    @NotNull(message = "Account name cannot be null")
    @Size(min = 2, max = 55, message = "Account name must be between 2 and 55 characters")
    private String name;
    @NotNull
    private UserRequestDTO user;
}
