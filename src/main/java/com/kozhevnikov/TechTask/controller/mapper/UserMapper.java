package com.kozhevnikov.TechTask.controller.mapper;

import com.kozhevnikov.TechTask.controller.dto.FullUserDto;
import com.kozhevnikov.TechTask.controller.dto.UserDto;
import com.kozhevnikov.TechTask.model.User;
import com.kozhevnikov.TechTask.model.enums.Status;
import com.kozhevnikov.TechTask.security.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserDto toDto(User user){
        return UserDto.builder()
                    .username(user.getUsername())
                    .status(user.getStatus().name())
                    .role(user.getRole().name())
                    .build();
    }

    public User fromFullDto(FullUserDto user) {
        return User.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(Optional.ofNullable(user.getPassword()).map(passwordEncoder::encode).orElse(null))
                .status(Optional.ofNullable(user.getStatus()).map(Status::valueOf).orElse(null))
                .role(Optional.ofNullable(user.getRole()).map(Role::valueOf).orElse(null))
                .build();
    }

    public FullUserDto toFullUserDto(User user) {
        return FullUserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .status(user.getStatus().name())
                .role(user.getRole().name())
                .build();
    }
}
