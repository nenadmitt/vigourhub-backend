package com.vigourhub.backend.service.mapper;

import com.vigourhub.backend.domain.entity.account.Account;
import com.vigourhub.backend.domain.entity.account.User;
import com.vigourhub.backend.dto.accounts.AccountRequestDTO;
import com.vigourhub.backend.dto.accounts.AccountResponseDTO;
import com.vigourhub.backend.dto.accounts.UserRequestDTO;
import com.vigourhub.backend.dto.accounts.UserResponseDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import javax.management.relation.Role;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {


    @Mapping(source = "account.id", target = "id")
    @Mapping(source = "user", target="owner")
    AccountResponseDTO toAccountDTO(Account account, User user);
    @Mapping(target = "roles", ignore = true)
    UserResponseDTO toUserResponseDTO(User user);

    Account toAccountDomain(AccountRequestDTO accountRequestDTO);

    User toUserDomain(UserRequestDTO requestDTO);

    @Named("getRoleNames")
    default List<String> map(List<Role> names) {
        return names.stream().map(Role::getRoleName).collect(Collectors.toList());
    };

}
