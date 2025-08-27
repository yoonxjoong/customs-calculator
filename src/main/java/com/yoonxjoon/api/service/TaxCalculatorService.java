package com.yoonxjoon.api.service;

import com.yoonxjoon.api.domain.dto.TaxCalculationRequest;
import com.yoonxjoon.api.domain.dto.TaxCalculationResponse;
import com.yoonxjoon.api.domain.entity.ProductEntity;
import com.yoonxjoon.api.domain.model.Price;
import com.yoonxjoon.api.domain.model.Product;
import com.yoonxjoon.api.domain.model.Ratio;
import com.yoonxjoon.api.domain.model.TransFee;
import com.yoonxjoon.api.domain.repository.ProductRepository;
import com.yoonxjoon.api.mapper.TaxCalculatorMapper;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaxCalculatorService {

    private final TaxCalculatorMapper taxCalculatorMapper;
    private final TaxCalculatorFactory taxCalculatorFactory;
    private final ProductPreprocessor productPreprocessor;
    private final ProductRepository productRepository;

    public TaxCalculationResponse calculate(TaxCalculationRequest request) {
        Product productFromRequest = taxCalculatorMapper.toDomain(request);

        Optional<ProductEntity> optionalProductEntity = productRepository.findById(
                request.getProductId());

        Product finalProduct = optionalProductEntity
                .map(entity -> {
                    return productFromRequest.withName(entity.getName())
                            .withCalculatorType(entity.getCalculatorType())
                            .withRatios(
                                    taxCalculatorMapper.toModelRatios(entity.getRatioEntitiy()));
                })
                .orElseThrow(() -> new NoSuchElementException(
                        "Product not found with id: " + productFromRequest.getId()));

        // Preprocessor를 사용해 데이터 가공
        List<Price> pricesWithExRate = productPreprocessor.addExchangedPrices(
                finalProduct.getPrices());

        List<Ratio> ratios = productPreprocessor.addTaxRatio(finalProduct);

        List<TransFee> transFees = productPreprocessor.addTransFee(
                finalProduct.getTransFees()
        );

        // with 메서드로 불변성을 유지하며 새로운 Product 객체 생성
        Product processedProduct = finalProduct.toBuilder()
                .prices(pricesWithExRate)
                .ratios(ratios)
                .transFees(transFees)
                .build();

        if (!processedProduct.taxYn()) {
            return taxCalculatorMapper.toZeroDto(processedProduct);
        }

        // 팩토리 패턴으로 계산기 객체 획득
        TaxCalculator taxCalculator = taxCalculatorFactory.getCalculator(
                processedProduct.getCalculatorType());

        // 계산 로직 실행. calculate()는 새로운 Product를 반환
        Product calculatedProduct = taxCalculator.calculate(processedProduct);

        // 최종 결과를 DTO로 변환
        return taxCalculatorMapper.toDto(calculatedProduct);
    }

}
