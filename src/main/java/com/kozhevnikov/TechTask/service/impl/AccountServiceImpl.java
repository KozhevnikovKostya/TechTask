package com.kozhevnikov.TechTask.service.impl;

import com.kozhevnikov.TechTask.model.Account;
import com.kozhevnikov.TechTask.model.enums.Operation;
import com.kozhevnikov.TechTask.service.interfaces.AccountService;

import java.math.BigDecimal;
import java.util.List;

public class AccountServiceImpl implements AccountService {
    @Override
    public List<Account> getAll() {
        return null;
    }

    @Override
    public Account create(Account account) {
        return null;
    }

    @Override
    public Account getById(Long id) {
        return null;
    }

    @Override
    public Account ATMOperation(BigDecimal amount, Operation operation) {
        return null;
    }

    @Override
    public Account moneyTransfer(BigDecimal amount, Long moneySender, Long moneyRecipient) {
        return null;
    }

    @Override
    public Account update(Account account) {
        return null;
    }
}
