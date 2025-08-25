package com.yoonxjoon.api.service;

import com.yoonxjoon.api.constant.CalculatorType;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TaxCalculatorFactoryImpl implements TaxCalculatorFactory{

    private final ApplicationContext context;

    @Override
    public TaxCalculator getCalculator(CalculatorType type) {
        return switch (type) {
            case CALCULATOR_CODE_1 -> context.getBean(TaxCalculator1.class);
            case CALCULATOR_CODE_2 -> context.getBean(TaxCalculator2.class);
            case CALCULATOR_CODE_3 -> context.getBean(TaxCalculator3.class);
            default -> throw new IllegalArgumentException("지원하지 않는 계산기 타입입니다: " + type);
        };
    }
}
