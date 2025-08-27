package com.yoonxjoon.api.mapper;

import com.yoonxjoon.api.constant.CurUnitCd;
import com.yoonxjoon.api.domain.dto.TaxCalculationRequest;
import com.yoonxjoon.api.domain.dto.TaxCalculationResponse;
import com.yoonxjoon.api.domain.entity.ProductEntity;
import com.yoonxjoon.api.domain.entity.RatioEntity;
import com.yoonxjoon.api.domain.model.Price;
import com.yoonxjoon.api.domain.model.Product;
import com.yoonxjoon.api.domain.model.Ratio;
import com.yoonxjoon.api.domain.model.TransFee;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class TaxCalculatorMapper {
    public Product toDomain(TaxCalculationRequest request) {
        if (request == null) {
            return null;
        }

        CurUnitCd curUnitCd = CurUnitCd.fromString(request.getCurUnitCd());

        List<Price> prices = List.of(Price.builder()
                .amount(request.getPrice())
                .curUnitCd(curUnitCd)
                .build());

        CurUnitCd transFeeUnitCd = CurUnitCd.fromString(request.getTransFeeUnitCd());
        List<TransFee> transFees = List.of(TransFee.builder()
                .amount(request.getTransFee())
                .curUnitCd(transFeeUnitCd)
                .build());

      return Product.builder()
                .id(request.getProductId())
                .categoryId(request.getCategoryId())
                .prices(prices)
                .transFees(transFees)
                .build();
    }

    public TaxCalculationResponse toDto(Product product) {
        if(product == null) {
            return null;
        }

        BigDecimal totalTaxAmount = product.getRatios().stream()
                .map(Ratio::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return TaxCalculationResponse.builder()
                .productId(product.getId())
                .categoryId(product.getCategoryId())
                .sumTax(totalTaxAmount.toString())
                .build();
    }

    public TaxCalculationResponse toZeroDto(Product product) {
        if(product == null) {
            return null;
        }

        return TaxCalculationResponse.builder()
                .productId(product.getId())
                .categoryId(product.getCategoryId())
                .sumTax(BigDecimal.ZERO.toString())
                .build();
    }

    public Product toDomain(ProductEntity entityProduct) {
        if (entityProduct == null) {
            return null;
        }

        // 엔티티의 `ratios` 리스트를 모델의 `ratios` 리스트로 변환
        List<Ratio> modelRatios = (entityProduct.getRatioEntitiy() != null)
                ? entityProduct.getRatioEntitiy().stream()
                .map(this::toModelRatio)
                .collect(Collectors.toList())
                : Collections.emptyList();

        return Product.builder()
                .id(entityProduct.getId())
                .categoryId(entityProduct.getCategoryEntity().getId())
                .name(entityProduct.getName())
                .calculatorType(entityProduct.getCalculatorType())
                .ratios(modelRatios)
                .build();
    }


    /**
     * `entity.Ratio` 리스트를 `model.Ratio` 리스트로 변환합니다.
     */
    public List<Ratio> toModelRatios(List<RatioEntity> entityRatios) {
        if (entityRatios == null) {
            return Collections.emptyList();
        }
        return entityRatios.stream()
                .map(this::toModelRatio)
                .collect(Collectors.toList());
    }


    // `entity.Ratio`를 `model.Ratio`로 변환하는 헬퍼 메서드
    private Ratio toModelRatio(RatioEntity entityRatio) {
        if (entityRatio == null) {
            return null;
        }
        return Ratio.builder()
                .ratioTypeCd(entityRatio.getRatioTypeCd())
                .value(entityRatio.getValue())
                .build();
    }
}
