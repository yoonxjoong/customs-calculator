package com.yoonxjoon.api.service;

import com.yoonxjoon.api.constant.RatioTypeCd;
import com.yoonxjoon.api.domain.model.BasisAboveProduct;
import com.yoonxjoon.api.domain.model.Product;
import com.yoonxjoon.api.domain.model.Ratio;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * 관세, 개별소비세, 부가세
 * 관세[과세가격*관세율]+개별소비세[(과세가격+관세-기준초과금)*기본소비세율]+부가세[(과세가격+관세+개별소비세)*부가세율]
 */
@Component
@Slf4j
public class TaxCalculator8 extends AbstractTaxCalculator {

    @Override
    public Product calculate(Product product) {

        if (!(product instanceof BasisAboveProduct basisAboveProduct)) {
            // 타입이 다르면 예외를 발생시키거나 다른 처리를 합니다.
            throw new IllegalArgumentException("이 계산기는 BasisAboveProduct만 처리할 수 있습니다.");
        }

        BigDecimal originAmount = basisAboveProduct.getOriginAmount();
        BigDecimal basisAboveAmount = basisAboveProduct.getBasisAboveAmount();
        Map<RatioTypeCd, Ratio> ratioMap = basisAboveProduct.getRatioMap();

        // 관세 = (과세가격 * 관세율)
        Ratio customsRatio = getRatioByType(ratioMap, RatioTypeCd.CUSTOMS);
        BigDecimal customs = originAmount.multiply(customsRatio.getValue());

        // 개별소비세 = (과세가격+관세-기준초과금)*기본소비세율
        Ratio basisSalesRatio = getRatioByType(ratioMap, RatioTypeCd.BASE_SALES);
        BigDecimal basisSales = (originAmount.add(customs).subtract(basisAboveAmount))
                .multiply(basisSalesRatio.getValue());

        // 부가세 = (과세가격+관세+개별소비세)*부가세율
        Ratio vatRatio = getRatioByType(ratioMap, RatioTypeCd.VAT);
        BigDecimal vat = (originAmount.add(customs).add(basisSales)).multiply(
                vatRatio.getValue());

        Ratio newCustomsRatio = customsRatio.withAmount(customs);
        Ratio newBasisSalesRatio = basisSalesRatio.withAmount(basisSales);
        Ratio newVatRatio = vatRatio.withAmount(vat);

        return product.withRatios(
                List.of(newCustomsRatio, newBasisSalesRatio,  newVatRatio));
    }
}
