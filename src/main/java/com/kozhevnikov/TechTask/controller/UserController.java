package com.kozhevnikov.TechTask.controller;

import com.kozhevnikov.TechTask.controller.dto.UserDto;
import com.kozhevnikov.TechTask.controller.mapper.UserMapper;
import com.kozhevnikov.TechTask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> getALlUsers(){
        return ResponseEntity.ok(userRepository.findAll().stream().map(userMapper::toDto).collect(Collectors.toList()));
    }
}
