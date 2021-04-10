package com.kozhevnikov.TechTask.service.interfaces;

import java.math.BigDecimal;

public interface ConversionService {

    public BigDecimal convert(BigDecimal amount, Long currentCurrencyId, Long resultCurrencyId);

}
