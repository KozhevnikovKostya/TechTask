package com.kozhevnikov.TechTask.controller.mapper;

import com.kozhevnikov.TechTask.controller.dto.UserDto;
import com.kozhevnikov.TechTask.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User user){
        return UserDto.builder()
                    .username(user.getUsername())
                    .status(user.getStatus().name())
                    .role(user.getRole().name())
                    .build();
    }

}
