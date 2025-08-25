package com.yoonxjoon.api.service;

import com.yoonxjoon.api.constant.CalculatorType;

public interface TaxCalculatorFactory {
    public TaxCalculator getCalculator(CalculatorType type);
}
