package com.kozhevnikov.TechTask.service.interfaces;

import com.kozhevnikov.TechTask.model.Account;
import com.kozhevnikov.TechTask.model.enums.Operation;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.util.List;

public interface AccountService {

    public List<Account> getAll();

    public Account create(Account account);

    public Account getById(Long id) throws AccessDeniedException;

    public Account ATMOperation(Long id, BigDecimal amount, Operation operation) throws AccessDeniedException;

    public Account moneyTransfer(BigDecimal amount, Long moneySender, Long moneyRecipient) throws AccessDeniedException;

    public Account update(Long id, Account account) throws AccessDeniedException;

    public Long delete(Long id) throws AccessDeniedException;

}
