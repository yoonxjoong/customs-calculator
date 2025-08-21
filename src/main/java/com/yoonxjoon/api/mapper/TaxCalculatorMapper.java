package com.yoonxjoon.api.mapper;

import com.yoonxjoon.api.constant.CurUnitCd;
import com.yoonxjoon.api.domain.model.Price;
import com.yoonxjoon.api.domain.model.Product;
import com.yoonxjoon.api.dto.TaxCalculationRequest;
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

        return Product.builder()
                .id(request.getProductId())
                .categoryId(request.getCategoryId())
                .transFee(request.getTransFee())
                .prices(prices)
                .build();
    }
}
