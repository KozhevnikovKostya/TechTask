package com.kozhevnikov.TechTask.repository;

import com.kozhevnikov.TechTask.model.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.chrono.Chronology;

public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Long> {

    @Query(value = "SELECT * FROM account_history where operation_time = (select Max(operation_time) From account_history where account_id = :accountId)", nativeQuery = true)
    public AccountHistory findLastHistoryByAccountId(@Param("accountId") Long accountId);
}
