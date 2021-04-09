package com.kozhevnikov.TechTask.repository;

import com.kozhevnikov.TechTask.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
