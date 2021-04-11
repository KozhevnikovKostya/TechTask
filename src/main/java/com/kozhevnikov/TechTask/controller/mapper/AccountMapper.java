package com.kozhevnikov.TechTask.controller.mapper;

import com.kozhevnikov.TechTask.controller.dto.AccountDto;
import com.kozhevnikov.TechTask.model.Account;
import com.kozhevnikov.TechTask.model.enums.Status;
import com.kozhevnikov.TechTask.security.AuthenticatedUser;
import com.kozhevnikov.TechTask.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountMapper {

    private final UserService userService;
    private final AuthenticatedUser authenticatedUser;

    public AccountDto toDto(Account account){
        return AccountDto.builder()
                    .id(account.getId())
                    .userId(account.getUser().getId())
                    .total(account.getTotal())
                    .status(account.getStatus().name())
                    .build();
    }

    public Account fromDto(AccountDto account){
        return Account.builder()
                .id(Optional.ofNullable(account.getId()).orElse(null))
                .user(userService.getUserById(authenticatedUser.getCurrentUserId()))
                .total(account.getTotal())
                .status(Optional.ofNullable(account.getStatus()).map(Status::valueOf).orElse(null))
                .build();
    }
}
