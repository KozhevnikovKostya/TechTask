package com.kozhevnikov.TechTask.service.impl;

import com.kozhevnikov.TechTask.model.Currency;
import com.kozhevnikov.TechTask.repository.CurrencyRepository;
import com.kozhevnikov.TechTask.exceptions.ResourceNotFoundException;
import com.kozhevnikov.TechTask.service.interfaces.ConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class ConversionServiceImpl implements ConversionService {

    private final CurrencyRepository currencyRepository;

    @Override
    public BigDecimal convert(BigDecimal amount, Long currentCurrencyId, Long resultCurrencyId) {
        if(currentCurrencyId.equals(resultCurrencyId)){
            return amount;
        }
        var currentCurrency = currencyRepository
                .findById(currentCurrencyId).map(Currency::getRate)
                .orElseThrow(()-> new ResourceNotFoundException(String.format("Currency with id: %s doesn't exist", currentCurrencyId)));
        var resultCurrency = currencyRepository.
                findById(currentCurrencyId).map(Currency::getRate)
                .orElseThrow(()-> new ResourceNotFoundException(String.format("Currency with id: %s doesn't exist", resultCurrencyId)));

        return currentCurrency.divide(resultCurrency).setScale(2, RoundingMode.HALF_DOWN);
    }
}
