package com.yoonxjoon.api.service;

import com.yoonxjoon.api.constant.CalculatorType;
import com.yoonxjoon.api.domain.model.Price;
import com.yoonxjoon.api.domain.model.Product;
import com.yoonxjoon.api.domain.model.Ratio;
import com.yoonxjoon.api.domain.model.TransFee;
import com.yoonxjoon.api.dto.TaxCalculationRequest;
import com.yoonxjoon.api.dto.TaxCalculationResponse;
import com.yoonxjoon.api.mapper.TaxCalculatorMapper;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaxCalculatorService {

    private final TaxCalculatorMapper taxCalculatorMapper;
    private final TaxCalculatorFactory taxCalculatorFactory;
    private final ProductPreprocessor productPreprocessor;

    public TaxCalculationResponse calculate(TaxCalculationRequest request) {
        Product originalProduct = taxCalculatorMapper.toDomain(request);

        // Preprocessor를 사용해 데이터 가공
        List<Price> pricesWithExRate = productPreprocessor.addExchangedPrices(
                originalProduct.getPrices()
        );

        List<Ratio> ratios = productPreprocessor.addTaxRatio(originalProduct);

        List<TransFee> transFees = productPreprocessor.addTransFee(
                originalProduct.getTransFees()
        );

        // with 메서드로 불변성을 유지하며 새로운 Product 객체 생성
        Product processedProduct = originalProduct
                .withPrices(pricesWithExRate)
                .withRatios(ratios)
                .withTransFees(transFees);

        // 팩토리 패턴으로 계산기 객체 획득
        TaxCalculator taxCalculator = taxCalculatorFactory.getCalculator(
                CalculatorType.CALCULATOR_CODE_3
        );

        // 계산 로직 실행. calculate()는 새로운 Product를 반환
        Product ret = taxCalculator.calculate(processedProduct);

        // 최종 결과를 DTO로 변환
        return taxCalculatorMapper.toDto(ret);
    }

}
