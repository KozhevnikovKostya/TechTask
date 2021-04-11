package com.kozhevnikov.TechTask.model;

import com.kozhevnikov.TechTask.model.enums.Operation;
import com.sun.xml.bind.v2.model.core.ID;
import jdk.jfr.DataAmount;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private LocalDateTime operationTime;

    @Enumerated(EnumType.STRING)
    private Operation operation;

    private BigDecimal totalAmount;

    private BigDecimal changingAmount;


}
