package com.kozhevnikov.TechTask.helper;

import com.kozhevnikov.TechTask.model.Account;
import com.kozhevnikov.TechTask.model.AccountHistory;
import com.kozhevnikov.TechTask.model.enums.Operation;
import com.kozhevnikov.TechTask.repository.AccountHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AdviceName;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
public class AccountAdvice {

    private final AccountHistoryRepository accountHistoryRepository;

    @AfterReturning(pointcut = "@annotation(com.kozhevnikov.TechTask.annotations.AccountHistory)", returning = "account")
    public void createHistoryAfterAccountOperations(JoinPoint joinPoint, Account account) throws Throwable{
        var lastAccountHistory = accountHistoryRepository.findLastHistoryByAccountId(account.getId());

        Operation operation;
        BigDecimal changingAmount;
        if(Objects.isNull(lastAccountHistory)){
            operation = Operation.CREATION;
            changingAmount = BigDecimal.ZERO;
        } else {
            changingAmount = account.getTotal().subtract(lastAccountHistory.getTotalAmount()).setScale(2, RoundingMode.HALF_DOWN);
            if(changingAmount.compareTo(BigDecimal.ZERO) < 0){
                operation = Operation.WITHDRAWAL;
            } else {
                operation = Operation.REFILL;
            }
        }
        var actualAccountHistory = AccountHistory.builder()
                                                 .account(account)
                                                 .changingAmount(changingAmount)
                                                 .operation(operation)
                                                 .operationTime(LocalDateTime.now())
                                                 .totalAmount(account.getTotal())
                                                 .build();
        accountHistoryRepository.save(actualAccountHistory);
        System.out.println("History created");
    }
}
