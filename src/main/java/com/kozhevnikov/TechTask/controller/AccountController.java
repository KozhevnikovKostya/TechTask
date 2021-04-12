package com.kozhevnikov.TechTask.controller;

import com.kozhevnikov.TechTask.controller.dto.AccountDto;
import com.kozhevnikov.TechTask.controller.mapper.AccountMapper;
import com.kozhevnikov.TechTask.exceptions.BankException;
import com.kozhevnikov.TechTask.model.Account;
import com.kozhevnikov.TechTask.model.enums.Operation;
import com.kozhevnikov.TechTask.service.interfaces.AccountService;
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
    public ResponseEntity<List<AccountDto>> getALlAccounts(){
        return ResponseEntity.ok(accountService.getAll().stream()
                    .map(accountMapper::toDto)
                    .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<AccountDto> create(@Valid @RequestBody AccountDto accountDto) throws BankException {
        Account account = accountMapper.fromDto(accountDto);
        return ResponseEntity.ok(accountMapper.toDto(accountService.create(account)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getById(@PathVariable Long id) throws AccessDeniedException {
        return ResponseEntity.ok(accountMapper.toDto(accountService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> update(@PathVariable Long id, @RequestBody AccountDto accountDto) throws IllegalAccessException, AccessDeniedException {
        Account account = accountMapper.fromDto(accountDto);
        return ResponseEntity.ok(accountMapper.toDto(accountService.update(id, account)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) throws AccessDeniedException {
        return ResponseEntity.ok(accountService.delete(id));
    }

    @PostMapping("/{id}/ATM")
    public ResponseEntity<AccountDto> atmOperation(@PathVariable(name = "id") Long accountId,
                                                   @RequestParam Operation operation,
                                                   @RequestBody BigDecimal amount) throws AccessDeniedException {

        Account account = accountService.ATMOperation(accountId, amount, operation);
        return ResponseEntity.ok(accountMapper.toDto(account));
    }

}
