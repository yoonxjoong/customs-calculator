package com.yoonxjoon.api.service;

import com.yoonxjoon.api.constant.RatioTypeCd;
import com.yoonxjoon.api.domain.model.Product;
import com.yoonxjoon.api.domain.model.Ratio;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TaxCalculator1 extends AbstractTaxCalculator {

    @Override
    public Product calculate(Product product) {
        BigDecimal originAmount = product.getOriginAmount();

        Map<RatioTypeCd, Ratio> ratioMap = product.getRatioMap();

        // 관세 = (과세가격 * 관세율)
        Ratio customsRatio = getRatioByType(ratioMap, RatioTypeCd.CUSTOMS);
        BigDecimal customs = originAmount.multiply(customsRatio.getValue());

        // 부가세 = (과세가격 + 관세) * 부가세율
        Ratio vatRatio = getRatioByType(ratioMap, RatioTypeCd.VAT);
        BigDecimal tmpVat = originAmount.add(customs);
        BigDecimal vat = tmpVat.multiply(vatRatio.getValue());

        Ratio newCustomsRatio = customsRatio.withAmount(customs);
        Ratio newVatRatio = vatRatio.withAmount(vat);

        return product.withRatios(List.of(newCustomsRatio, newVatRatio));

    }
}
