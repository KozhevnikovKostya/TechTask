package com.kozhevnikov.TechTask.repository;

import com.kozhevnikov.TechTask.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByUserId(Long id);
}
