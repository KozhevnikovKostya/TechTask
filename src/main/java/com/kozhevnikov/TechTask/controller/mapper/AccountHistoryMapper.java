package com.kozhevnikov.TechTask.controller.mapper;

import com.kozhevnikov.TechTask.model.dto.AccountHistoryDto;
import com.kozhevnikov.TechTask.model.AccountHistory;
import org.springframework.stereotype.Component;

@Component
public class AccountHistoryMapper {
    public AccountHistoryDto toDto(AccountHistory history){
        return AccountHistoryDto.builder()
                                .id(history.getId())
                                .accountId(history.getAccount().getId())
                                .operation(history.getOperation().name())
                                .operationTime(history.getOperationTime().toString())
                                .totalAmount(history.getTotalAmount())
                                .changingAmount(history.getChangingAmount())
                                .build();
    }
}
