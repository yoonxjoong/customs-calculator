package com.yoonxjoon.api.mapper;

import com.yoonxjoon.api.constant.CurUnitCd;
import com.yoonxjoon.api.domain.model.Price;
import com.yoonxjoon.api.domain.model.Product;
import com.yoonxjoon.api.domain.model.Ratio;
import com.yoonxjoon.api.domain.model.TransFee;
import com.yoonxjoon.api.domain.dto.TaxCalculationRequest;
import com.yoonxjoon.api.domain.dto.TaxCalculationResponse;
import java.math.BigDecimal;
import java.util.List;
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
                .tax(totalTaxAmount.toString())
                .build();
    }
}
