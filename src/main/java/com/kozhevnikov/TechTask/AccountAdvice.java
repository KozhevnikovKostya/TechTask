package com.kozhevnikov.TechTask;

import com.kozhevnikov.TechTask.model.Account;
import com.kozhevnikov.TechTask.repository.AccountHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AdviceName;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AccountAdvice {

    private final AccountHistoryRepository accountHistoryRepository;

    @AfterReturning(pointcut = "@annotation(com.kozhevnikov.TechTask.annotations.AccountHistory)", returning = "account")
    public void createHistoryAfterAccountOperations(JoinPoint joinPoint, Account account) throws Throwable{
        System.out.println(joinPoint + "advice working " + account.getId());
    }
}
