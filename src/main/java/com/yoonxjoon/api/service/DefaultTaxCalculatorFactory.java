package com.yoonxjoon.api.service;

import com.yoonxjoon.api.constant.CalculatorType;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultTaxCalculatorFactory implements TaxCalculatorFactory{

    private final Map<String, TaxCalculator> calculators;

    @Override
    public TaxCalculator getCalculator(CalculatorType type) {
        return switch (type) {
            case CALCULATOR_CODE_1 -> calculators.get("taxCalculator1");
            case CALCULATOR_CODE_2 -> calculators.get("taxCalculator2");
            case CALCULATOR_CODE_3 -> calculators.get("taxCalculator3");
            default -> throw new IllegalArgumentException("지원하지 않는 계산기 타입입니다: " + type);
        };
    }
}
