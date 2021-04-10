package com.kozhevnikov.TechTask.controller;

import com.kozhevnikov.TechTask.controller.dto.FullUserDto;
import com.kozhevnikov.TechTask.controller.dto.UserDto;
import com.kozhevnikov.TechTask.controller.mapper.UserMapper;
import com.kozhevnikov.TechTask.exceptions.BankException;
import com.kozhevnikov.TechTask.model.User;
import com.kozhevnikov.TechTask.repository.UserRepository;
import com.kozhevnikov.TechTask.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> getALlUsers(){
        return ResponseEntity.ok(userService.getAllUsers().stream().map(userMapper::toDto).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<FullUserDto> create(@Valid @RequestBody FullUserDto userDto) throws BankException {
        User user = userMapper.fromFullDto(userDto);
        return ResponseEntity.ok(userMapper.toFullUserDto(userService.createUser(user)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullUserDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(userMapper.toFullUserDto(userService.getUserById(id)));
    }
}
