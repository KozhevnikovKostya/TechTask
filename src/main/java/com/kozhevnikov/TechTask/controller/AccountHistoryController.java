package com.kozhevnikov.TechTask.controller;

import com.kozhevnikov.TechTask.controller.dto.AccountHistoryDto;
import com.kozhevnikov.TechTask.controller.mapper.AccountHistoryMapper;
import com.kozhevnikov.TechTask.model.AccountHistory;
import com.kozhevnikov.TechTask.repository.AccountHistoryRepository;
import com.kozhevnikov.TechTask.service.interfaces.AccountHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
public class AccountHistoryController {
    private final AccountHistoryService accountHistoryService;
    private final AccountHistoryMapper accountHistoryMapper;

    @GetMapping("/{accountId}")
    public ResponseEntity<List<AccountHistoryDto>> getAllHistoryByAccount(@PathVariable Long accountId){
        return ResponseEntity.ok(accountHistoryService.getAllHistoryByAccount(accountId).stream()
                                                      .map(accountHistoryMapper::toDto)
                                                      .collect(Collectors.toList()));
    }
}
