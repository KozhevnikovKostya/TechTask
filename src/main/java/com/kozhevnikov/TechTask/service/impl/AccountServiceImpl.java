package com.kozhevnikov.TechTask.service.impl;

import com.kozhevnikov.TechTask.annotations.AccountHistory;
import com.kozhevnikov.TechTask.exceptions.BankException;
import com.kozhevnikov.TechTask.exceptions.ResourceNotFoundException;
import com.kozhevnikov.TechTask.model.Account;
import com.kozhevnikov.TechTask.model.enums.Operation;
import com.kozhevnikov.TechTask.repository.AccountRepository;
import com.kozhevnikov.TechTask.security.AuthenticatedUser;
import com.kozhevnikov.TechTask.security.model.Role;
import com.kozhevnikov.TechTask.service.interfaces.AccountService;
import com.kozhevnikov.TechTask.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;
    private final AuthenticatedUser authenticatedUser;

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Transactional
    @Override
    @AccountHistory
    public Account create(Account account) {
        account.setUser(userService.getUserById(authenticatedUser.getCurrentUserId()));
        account.setTotal(BigDecimal.valueOf(0,2));
        return accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    @Override
    public Account getById(Long id) throws AccessDeniedException {
        Account account = accountRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(String.format("Account with id: %s doesn't exist", id)));
        checkAccess(account);
        return account;
    }

    @Transactional
    @Override
    @AccountHistory
    public Account ATMOperation(Long id, BigDecimal amount, Operation operation) throws AccessDeniedException {
        Account account = getById(id);
        if (operation.equals(Operation.WITHDRAWAL)){
            checkAccountAmount(account, amount);
            BigDecimal updatedTotal = account.getTotal().subtract(amount).setScale(2, RoundingMode.HALF_DOWN);
            account.setTotal(updatedTotal);
        } else {
            BigDecimal updatedTotal = account.getTotal().add(amount).setScale(2, RoundingMode.HALF_DOWN);
            account.setTotal(updatedTotal);
        }
        System.out.println("atm");
        return accountRepository.save(account);
    }

    @Transactional
    @Override
    public Account update(Long id, Account account) throws AccessDeniedException {
        Account foundAccount = accountRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(String.format("Account with id: %s doesn't exist", id)));
        checkAccess(foundAccount);
        updateFields(foundAccount, account);
        return account;
    }



    @Override
    public Long delete(Long id) throws AccessDeniedException {
        checkAccess(getById(id));
        accountRepository.deleteById(id);
        return id;
    }

    private void updateFields(Account foundAccount, Account account) {
        foundAccount.setStatus(Optional.ofNullable(account.getStatus()).orElse(null));
    }

    private void checkAccess(Account account) throws AccessDeniedException {
        if(!account.getUser().getId().equals(authenticatedUser.getCurrentUserId()) && !authenticatedUser.hasRole(Role.ADMIN)){
            throw new AccessDeniedException("You don't have access to this operation");
        }

    }

    private void checkAccountAmount(Account account, BigDecimal operationAmount){
        if (account.getTotal().compareTo(operationAmount) < 0){
            throw new BankException("There are not enough funds in the account");
        }
    }


}
