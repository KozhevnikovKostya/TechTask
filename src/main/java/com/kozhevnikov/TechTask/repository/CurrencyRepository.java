package com.kozhevnikov.TechTask.repository;

import com.kozhevnikov.TechTask.model.Currency;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepository extends CrudRepository<Currency, Long> {
}
