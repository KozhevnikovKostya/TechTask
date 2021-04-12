package com.kozhevnikov.TechTask.service.interfaces;

import com.kozhevnikov.TechTask.model.AccountHistory;

import java.util.List;

/**
 * The service to showing account history
 */
public interface AccountHistoryService {
    /**
     *
     * @param id of account
     * @return list with account operations
     */
    public List<AccountHistory> getAllHistoryByAccount(Long id);
}
