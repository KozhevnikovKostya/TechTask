package com.kozhevnikov.TechTask.service.interfaces;

import com.kozhevnikov.TechTask.model.Account;
import com.kozhevnikov.TechTask.model.enums.Operation;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    public List<Account> getAll();

    public Account create(Account account);

    public Account getById(Long id);

    public Account ATMOperation(BigDecimal amount, Operation operation);

    public Account moneyTransfer(BigDecimal amount, Long moneySender, Long moneyRecipient);

    public Account update(Account account);

}
