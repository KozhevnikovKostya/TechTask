package com.kozhevnikov.TechTask.service.interfaces;

import com.kozhevnikov.TechTask.model.AccountHistory;

import java.util.List;

public interface AccountHistoryService {
    public List<AccountHistory> getAllHistoryByAccount(Long id);
}
