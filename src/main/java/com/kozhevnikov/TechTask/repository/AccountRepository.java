package com.kozhevnikov.TechTask.repository;

import com.kozhevnikov.TechTask.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
