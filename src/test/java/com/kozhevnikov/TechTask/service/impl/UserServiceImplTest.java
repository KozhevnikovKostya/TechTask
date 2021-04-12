package com.kozhevnikov.TechTask.service.impl;

import com.kozhevnikov.TechTask.exceptions.BankException;
import com.kozhevnikov.TechTask.model.Account;
import com.kozhevnikov.TechTask.model.User;
import com.kozhevnikov.TechTask.model.enums.Operation;
import com.kozhevnikov.TechTask.model.enums.Status;
import com.kozhevnikov.TechTask.repository.UserRepository;
import com.kozhevnikov.TechTask.security.AuthenticatedUser;
import com.kozhevnikov.TechTask.security.model.Role;
import com.kozhevnikov.TechTask.service.interfaces.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticatedUser authenticatedUser;

    private UserService userService;

    private User user;
    private User updatedUser;

    @BeforeEach
    public void init(){
        userService = new UserServiceImpl(userRepository, authenticatedUser);
        when(userRepository.save(any())).thenAnswer(i-> i.getArguments()[0]);
        user = User.builder()
                    .id(1L)
                    .password("user")
                    .username("user")
                    .lastName("Userov")
                    .firstName("User")
                    .build();
        updatedUser = User.builder()
                          .id(1L)
                          .password("user")
                          .username("user")
                          .status(Status.ACTIVE)
                          .role(Role.USER)
                          .lastName("Userovoshvily")
                          .firstName("Userichy")
                          .build();

    }
    @Test
    public void createUser() {
        User result = userService.createUser(user);
        verify(userRepository, times(1)).save(any());
        Assertions.assertEquals(result.getRole(), Role.USER);
        Assertions.assertEquals(result.getStatus(), Status.ACTIVE);
    }

    @Test
    public void updateUser() throws AccessDeniedException {
        User userWithNewFields = User.builder()
                            .lastName("Userovoshvily")
                            .firstName("Userichy")
                            .status(Status.ACTIVE)
                            .role(Role.USER)
                            .build();
        when(authenticatedUser.getCurrentUserId()).thenReturn(1L);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        User result = userService.updateUser(1L, userWithNewFields);
        Assertions.assertEquals(result, updatedUser);
    }

    @Test
    public void accessDeniedTest(){
        when(authenticatedUser.getCurrentUserId()).thenReturn(2L);
        assertThrows(AccessDeniedException.class,
                () -> userService.updateUser(1L, updatedUser));
    }

    @Test
    public void bankDuplicateUsernameTest(){
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(updatedUser));
        assertThrows(BankException.class,
                () -> userService.createUser(user));
    }
}