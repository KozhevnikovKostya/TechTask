package com.kozhevnikov.TechTask.controller;

import com.kozhevnikov.TechTask.model.dto.AccountDto;
import com.kozhevnikov.TechTask.controller.mapper.AccountMapper;
import com.kozhevnikov.TechTask.exceptions.BankException;
import com.kozhevnikov.TechTask.model.Account;
import com.kozhevnikov.TechTask.model.enums.Operation;
import com.kozhevnikov.TechTask.service.interfaces.AccountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @GetMapping
    @ApiOperation(value = "Get all account by authorized user", response = AccountDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get accounts"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<List<AccountDto>> getALlAccounts(){
        return ResponseEntity.ok(accountService.getAll().stream()
                    .map(accountMapper::toDto)
                    .collect(Collectors.toList()));
    }

    @PostMapping
    @ApiOperation(value = "Create account for authorized user", response = AccountDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully create account"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<AccountDto> create(@Valid @RequestBody AccountDto accountDto) throws BankException, AccessDeniedException {
        Account account = accountMapper.fromDto(accountDto);
        return ResponseEntity.ok(accountMapper.toDto(accountService.create(account)));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get  account by id", response = AccountDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get account"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<AccountDto> getById(@PathVariable Long id) throws AccessDeniedException {
        return ResponseEntity.ok(accountMapper.toDto(accountService.getById(id)));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update account status", response = AccountDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully update account"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<AccountDto> update(@PathVariable Long id, @RequestBody AccountDto accountDto) throws IllegalAccessException, AccessDeniedException {
        Account account = accountMapper.fromDto(accountDto);
        return ResponseEntity.ok(accountMapper.toDto(accountService.update(id, account)));
    }


    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete account", response = Long.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully delete account"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Long> delete(@PathVariable Long id) throws AccessDeniedException {
        return ResponseEntity.ok(accountService.delete(id));
    }
    @ApiOperation(value = "Perform ATM operations: refill or withdrawal only", response = AccountDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully perform the operation"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping("/{id}/atm")
    public ResponseEntity<AccountDto> atmOperation(@PathVariable(name = "id") Long accountId,
                                                   @RequestParam Operation operation,
                                                   @RequestBody BigDecimal amount) throws AccessDeniedException {

        Account account = accountService.atmOperation(accountId, amount, operation);
        return ResponseEntity.ok(accountMapper.toDto(account));
    }

}
