package com.yoonxjoon.api.service;

import static com.yoonxjoon.api.constant.CalculatorType.CALCULATOR_CODE_1;
import static com.yoonxjoon.api.constant.CalculatorType.CALCULATOR_CODE_2;
import static com.yoonxjoon.api.constant.CalculatorType.CALCULATOR_CODE_3;
import static com.yoonxjoon.api.constant.CalculatorType.CALCULATOR_CODE_4;
import static com.yoonxjoon.api.constant.CalculatorType.CALCULATOR_CODE_5;
import static com.yoonxjoon.api.constant.CalculatorType.CALCULATOR_CODE_6;
import static com.yoonxjoon.api.constant.CalculatorType.CALCULATOR_CODE_7;
import static com.yoonxjoon.api.constant.CalculatorType.CALCULATOR_CODE_8;

import com.yoonxjoon.api.constant.CalculatorType;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultTaxCalculatorFactory implements TaxCalculatorFactory{

    private final Map<String, TaxCalculator> calculators;

    @Override
    public TaxCalculator getCalculator(CalculatorType type) {
        return switch (type) {
            case CALCULATOR_CODE_1 -> calculators.get(CALCULATOR_CODE_1.getValue());
            case CALCULATOR_CODE_2 -> calculators.get(CALCULATOR_CODE_2.getValue());
            case CALCULATOR_CODE_3 -> calculators.get(CALCULATOR_CODE_3.getValue());
            case CALCULATOR_CODE_4 -> calculators.get(CALCULATOR_CODE_4.getValue());
            case CALCULATOR_CODE_5 -> calculators.get(CALCULATOR_CODE_5.getValue());
            case CALCULATOR_CODE_6 -> calculators.get(CALCULATOR_CODE_6.getValue());
            case CALCULATOR_CODE_7 -> calculators.get(CALCULATOR_CODE_7.getValue());
            case CALCULATOR_CODE_8 -> calculators.get(CALCULATOR_CODE_8.getValue());
            default -> throw new IllegalArgumentException("지원하지 않는 계산기 타입입니다: " + type);
        };
    }
}
