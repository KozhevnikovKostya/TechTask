package com.kozhevnikov.TechTask.service.impl;

import com.kozhevnikov.TechTask.exceptions.BankException;
import com.kozhevnikov.TechTask.model.Account;
import com.kozhevnikov.TechTask.model.User;
import com.kozhevnikov.TechTask.model.enums.Operation;
import com.kozhevnikov.TechTask.model.enums.Status;
import com.kozhevnikov.TechTask.repository.AccountRepository;
import com.kozhevnikov.TechTask.security.AuthenticatedUser;
import com.kozhevnikov.TechTask.security.model.Role;
import com.kozhevnikov.TechTask.service.interfaces.AccountService;
import com.kozhevnikov.TechTask.service.interfaces.UserService;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private  UserService userService;
    @Mock
    private  AuthenticatedUser authenticatedUser;
    private AccountService accountService;

    private Account account;


    @BeforeEach
    public void init(){
        accountService = new AccountServiceImpl(accountRepository, userService, authenticatedUser);
        when(accountRepository.save(any(Account.class))).thenAnswer(i->i.getArguments()[0]);
        User user = User.builder()
                        .id(1L)
                        .firstName("admin")
                        .lastName("admin")
                        .username("admin")
                        .password("admin")
                        .status(Status.ACTIVE)
                        .role(Role.USER)
                        .build();
        account = Account.builder()
                         .id(1L)
                         .status(Status.ACTIVE)
                         .total(new BigDecimal(300))
                         .user(user)
                         .build();
        user.setAccounts(List.of(account));
    }

    @Test
    public void atmWithdrawalOperation() throws AccessDeniedException {
        when(authenticatedUser.getCurrentUserId()).thenReturn(1L);
        when(accountRepository.findById(any())).thenReturn(Optional.of(account));
        Account resultAccount = accountService.atmOperation(1L, new BigDecimal(200), Operation.WITHDRAWAL);
        verify(accountRepository, times(1)).findById(any());
        verify(accountRepository, times(1)).save(any());
        Assertions.assertEquals(resultAccount.getTotal().setScale(2, RoundingMode.HALF_DOWN), new BigDecimal(100).setScale(2, RoundingMode.HALF_DOWN));
    }

    @Test
    public void atmRefillOperation() throws AccessDeniedException {
        when(authenticatedUser.getCurrentUserId()).thenReturn(1L);
        when(accountRepository.findById(any())).thenReturn(Optional.of(account));
        Account resultAccount = accountService.atmOperation(1L, new BigDecimal(197.983), Operation.REFILL);
        verify(accountRepository, times(1)).findById(any());
        verify(accountRepository, times(1)).save(any());
        Assertions.assertEquals(resultAccount.getTotal().setScale(2, RoundingMode.HALF_DOWN), new BigDecimal(497.983).setScale(2, RoundingMode.HALF_DOWN));
    }

    @Test
    public void atmNotEnoughFunds() throws AccessDeniedException {
        when(authenticatedUser.getCurrentUserId()).thenReturn(1L);
        when(accountRepository.findById(any())).thenReturn(Optional.of(account));
        assertThrows(BankException.class,
                () -> accountService.atmOperation(1L, new BigDecimal(900), Operation.WITHDRAWAL));
    }

    @Test
    public void atmAccessDenied(){
        when(authenticatedUser.getCurrentUserId()).thenReturn(2L);
        when(accountRepository.findById(any())).thenReturn(Optional.of(account));
        assertThrows(AccessDeniedException.class,
                () -> accountService.atmOperation(1L, new BigDecimal(100), Operation.WITHDRAWAL));
    }
}