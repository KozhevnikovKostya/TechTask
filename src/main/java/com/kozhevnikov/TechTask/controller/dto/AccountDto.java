package com.kozhevnikov.TechTask.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDto {
    private Long id;
    private Long userId;
    private BigDecimal total;
    private String status;
}
