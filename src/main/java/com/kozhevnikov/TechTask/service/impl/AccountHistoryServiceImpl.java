package com.kozhevnikov.TechTask.service.impl;

import com.kozhevnikov.TechTask.exceptions.ResourceNotFoundException;
import com.kozhevnikov.TechTask.model.AccountHistory;
import com.kozhevnikov.TechTask.repository.AccountHistoryRepository;
import com.kozhevnikov.TechTask.service.interfaces.AccountHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountHistoryServiceImpl implements AccountHistoryService {

    private final AccountHistoryRepository accountHistoryRepository;

    @Override
    public List<AccountHistory> getAllHistoryByAccount(Long id) {
        return accountHistoryRepository.findAllByAccount_Id(id);
    }
}
