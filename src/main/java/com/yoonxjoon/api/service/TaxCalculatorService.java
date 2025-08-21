package com.yoonxjoon.api.service;

import com.yoonxjoon.api.constant.CurUnitCd;
import com.yoonxjoon.api.domain.model.Price;
import com.yoonxjoon.api.domain.model.Product;
import com.yoonxjoon.api.dto.TaxCalculationRequest;
import com.yoonxjoon.api.dto.TaxCalculationResponse;
import com.yoonxjoon.api.mapper.TaxCalculatorMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaxCalculatorService {
    private final TaxCalculatorMapper taxCalculatorMapper;

    public TaxCalculationResponse calculate(TaxCalculationRequest request) {
        Product originalProduct = taxCalculatorMapper.toDomain(request);

        List<Price> pricesWithExRate = addExchangedPrices(originalProduct.getPrices());

        return new TaxCalculationResponse();
    }

    private List<Price> addExchangedPrices(List<Price> prices) {
        // 기존 prices 리스트를 복사하여 새로운 리스트를 만듭니다.
        List<Price> newPrices = new ArrayList<>(prices);

        Price sourcePrice = newPrices.get(0);
        if (sourcePrice == null) {
            return newPrices;
        }

        if (!sourcePrice.getCurUnitCd().equals(CurUnitCd.USD)) {
            Price usdPrice = Price.builder()
                    .amount(sourcePrice.getAmount().multiply(BigDecimal.ONE))
                    .curUnitCd(CurUnitCd.USD)
                    .build();
            newPrices.add(usdPrice);
        }

        Price targetPrice = Price.builder()
                .amount(sourcePrice.getAmount().multiply(BigDecimal.ONE))
                .curUnitCd(CurUnitCd.KRW)
                .build();
        newPrices.add(targetPrice);

        return List.copyOf(newPrices);
    }

}
