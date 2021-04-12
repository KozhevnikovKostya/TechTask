package com.kozhevnikov.TechTask.controller;

import com.kozhevnikov.TechTask.model.dto.AccountDto;
import com.kozhevnikov.TechTask.model.dto.FullUserDto;
import com.kozhevnikov.TechTask.model.dto.UserDto;
import com.kozhevnikov.TechTask.controller.mapper.UserMapper;
import com.kozhevnikov.TechTask.exceptions.BankException;
import com.kozhevnikov.TechTask.model.User;
import com.kozhevnikov.TechTask.service.interfaces.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    @ApiOperation(value = "Get all users", response = AccountDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get users"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<List<UserDto>> getALlUsers(){
        return ResponseEntity.ok(userService.getAllUsers().stream().map(userMapper::toDto).collect(Collectors.toList()));
    }

    @PostMapping
    @ApiOperation(value = "Create user ", response = AccountDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully create user"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<FullUserDto> create(@Valid @RequestBody FullUserDto userDto) throws BankException {
        User user = userMapper.fromFullDto(userDto);
        return ResponseEntity.ok(userMapper.toFullUserDto(userService.createUser(user)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get user by id", response = AccountDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get user"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<FullUserDto> getById(@PathVariable Long id) throws AccessDeniedException {
        return ResponseEntity.ok(userMapper.toFullUserDto(userService.getUserById(id)));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update user", response = AccountDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully update user"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<FullUserDto> update(@PathVariable Long id, @RequestBody FullUserDto userDto) throws AccessDeniedException {
        User user = userMapper.fromFullDto(userDto);
        return ResponseEntity.ok(userMapper.toFullUserDto(userService.updateUser(id, user)));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete user", response = Long.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully delete user"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Long> delete(@PathVariable Long id) throws AccessDeniedException {
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
