package com.kozhevnikov.TechTask.service.interfaces;

import com.kozhevnikov.TechTask.model.Account;
import com.kozhevnikov.TechTask.model.enums.Operation;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.util.List;

/**
 * The service for working with accounts
 */
public interface AccountService {

    /**
     * @return list of accounts by authorized user
     */
     List<Account> getAll();

    /**
     * create the account for authorized user
     *
     * @param account
     * @return created account
     */
     Account create(Account account) throws AccessDeniedException;

    /**
     *
     * @param id
     * @return requested account
     * @throws AccessDeniedException if authorized user isn't admin or this account doesn't belong to him
     */
     Account getById(Long id) throws AccessDeniedException;

    /**
     *
     * @param id
     * @param amount of operation
     * @param operation type (refill or withdrawal)
     * @return account state after operation
     * @throws AccessDeniedException if account doesn't belong to authorized user
     */
     Account atmOperation(Long id, BigDecimal amount, Operation operation) throws AccessDeniedException;

    /**
     * can update only status field
     *
     * @param id
     * @param account
     * @return updated account
     * @throws AccessDeniedException if account doesn't belong to authorized user
     */
     Account update(Long id, Account account) throws AccessDeniedException;

    /**
     * delete account by id, can perform only users with permission managed:user
     *
     * @param id
     * @return id of deleted account
     */
     Long delete(Long id);

}
