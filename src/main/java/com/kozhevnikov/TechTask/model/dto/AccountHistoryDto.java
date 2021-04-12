package com.kozhevnikov.TechTask.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccountHistoryDto {

    private Long id;
    private Long accountId;
    private String operationTime;
    private String operation;
    private BigDecimal totalAmount;
    private BigDecimal changingAmount;

}
