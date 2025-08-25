package com.yoonxjoon.api.service;

import com.yoonxjoon.api.constant.RatioTypeCd;
import com.yoonxjoon.api.domain.model.Product;
import com.yoonxjoon.api.domain.model.Ratio;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

// 부가세[과세가격*부가세율]
@Component
@Slf4j
public class TaxCalculator2 extends AbstractTaxCalculator {

    @Override
    public Product calculate(Product product) {
        BigDecimal originAmount = product.getOriginAmount();

        Map<RatioTypeCd, Ratio> ratioMap = product.getRatioMap();

        // 부가세 = 과세가격 * 부가세율
        Ratio vatRatio = getRatioByType(ratioMap, RatioTypeCd.VAT);
        BigDecimal vat = originAmount.multiply(vatRatio.getValue());

        Ratio newVatRatio = vatRatio.withAmount(vat);

        return product.withRatios(List.of(newVatRatio));

    }
}
