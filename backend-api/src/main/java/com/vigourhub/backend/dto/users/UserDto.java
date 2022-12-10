package com.vigourhub.backend.dto.users;

import com.vigourhub.backend.domain.models.account.Role;
import com.vigourhub.backend.domain.models.account.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserDto {
    private String id;
    private String accountId;
    private String accountName;
    private String username;
    private String firstName;
    private String lastName;
    private List<String> roles;

    public static UserDto fromDomain(User model) {

        var response = new UserDto();
        response.setId(model.getId().toString());
        response.setUsername(model.getUsername());
        response.setFirstName(model.getFirstName());
        response.setLastName(model.getLastName());

        var rolesResponse = model.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        response.setRoles(rolesResponse);

        var accountModel = model.getAccount();
        if (accountModel != null) {
            response.setAccountId(model.getAccount().getId().toString());
            response.setAccountName(model.getAccount().getName());
        }

        return response;
    }
}
