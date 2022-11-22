package com.vigourhub.backend.dto.accounts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientRegistrationRequestDto {

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
