package com.kozhevnikov.TechTask.controller.dto;

import com.kozhevnikov.TechTask.model.Account;
import com.kozhevnikov.TechTask.model.User;
import com.kozhevnikov.TechTask.model.enums.Operation;
import com.kozhevnikov.TechTask.model.enums.Status;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
