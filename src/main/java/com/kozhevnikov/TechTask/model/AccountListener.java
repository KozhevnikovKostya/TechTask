//package com.kozhevnikov.TechTask.model;
//
//import com.kozhevnikov.TechTask.model.enums.Operation;
//import com.kozhevnikov.TechTask.repository.AccountHistoryRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Configurable;
//
//import javax.persistence.PostPersist;
//import javax.persistence.PostUpdate;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.time.LocalDateTime;
//
//
//@Configurable
//public class AccountListener {
//
//    @Autowired
//    private AccountHistoryRepository accountHistoryRepository;
//
//    @PostPersist
//    private void afterCreation(Account account){
//        AccountHistory accountHistory = AccountHistory.builder()
//                .account(account)
//                .changingAmount(BigDecimal.ZERO)
//                .operation(Operation.CREATION)
//                .operationTime(LocalDateTime.now())
//                .totalAmount(account.getTotal())
//                .build();
//        accountHistoryRepository.save(accountHistory);
//    }
//
//    @PostUpdate
//    public void afterUpdate(Account account){
//        BigDecimal lastHistoryAmount = accountHistoryRepository.findLastHistoryByAccountId(account.getId()).getTotalAmount();
//        BigDecimal changingAmount = account.getTotal().subtract(lastHistoryAmount).setScale(2, RoundingMode.HALF_DOWN);
//        Operation operation;
//        if(changingAmount.compareTo(BigDecimal.ZERO) < 0){
//            operation = Operation.WITHDRAWAL;
//        }else {
//            operation = Operation.REFILL;
//        }
//        AccountHistory accountHistory = AccountHistory.builder()
//                .account(account)
//                .changingAmount(changingAmount)
//                .operation(operation)
//                .operationTime(LocalDateTime.now())
//                .totalAmount(account.getTotal())
//                .build();
//        accountHistoryRepository.save(accountHistory);
//    }
//}
