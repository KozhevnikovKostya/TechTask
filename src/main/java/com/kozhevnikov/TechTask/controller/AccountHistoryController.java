package com.kozhevnikov.TechTask.controller;

import com.kozhevnikov.TechTask.model.dto.AccountDto;
import com.kozhevnikov.TechTask.model.dto.AccountHistoryDto;
import com.kozhevnikov.TechTask.controller.mapper.AccountHistoryMapper;
import com.kozhevnikov.TechTask.service.interfaces.AccountHistoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation(value = "Get all history by account id", response = AccountHistoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get account history"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<List<AccountHistoryDto>> getAllHistoryByAccount(@PathVariable Long accountId){
        return ResponseEntity.ok(accountHistoryService.getAllHistoryByAccount(accountId).stream()
                                                      .map(accountHistoryMapper::toDto)
                                                      .collect(Collectors.toList()));
    }
}
