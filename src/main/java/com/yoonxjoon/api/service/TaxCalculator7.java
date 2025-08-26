package com.yoonxjoon.api.service;

import com.yoonxjoon.api.constant.RatioTypeCd;
import com.yoonxjoon.api.domain.model.Product;
import com.yoonxjoon.api.domain.model.Ratio;
import com.yoonxjoon.api.domain.model.RuralProduct;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * 관세,개별소비세, 농특세, 교육세, 부가세
 * 관세[과세가격*관세율]+개별소비세[(과세가격+관세-기준초과금)*기본소비세율]+농특세[개별소비세*농특세율]+교육세[개별소비세*교육세율]+부가세[(과세가격+관세+개별소비세+농특세+교육세)*부가세율]
 */
@Component
@Slf4j
public class TaxCalculator7 extends AbstractTaxCalculator {

    @Override
    public Product calculate(Product product) {

        if (!(product instanceof RuralProduct ruralProduct)) {
            // 타입이 다르면 예외를 발생시키거나 다른 처리를 합니다.
            throw new IllegalArgumentException("이 계산기는 RuralProduct만 처리할 수 있습니다.");
        }

        BigDecimal originAmount = ruralProduct.getOriginAmount();
        BigDecimal basisAboveAmount = ruralProduct.getBasisAboveAmount();
        Map<RatioTypeCd, Ratio> ratioMap = ruralProduct.getRatioMap();

        // 관세 = (과세가격 * 관세율)
        Ratio customsRatio = getRatioByType(ratioMap, RatioTypeCd.CUSTOMS);
        BigDecimal customs = originAmount.multiply(customsRatio.getValue());

        // 개별소비세 = (과세가격+관세-기준초과금)*기본소비세율
        Ratio basisSalesRatio = getRatioByType(ratioMap, RatioTypeCd.BASE_SALES);
        BigDecimal basisSales = (originAmount.add(customs).subtract(basisAboveAmount))
                .multiply(basisSalesRatio.getValue());

        // 농특세 = 개별소비세 * 농특세율
        Ratio ruralRatio = getRatioByType(ratioMap, RatioTypeCd.RURAL);
        BigDecimal rural = basisSales.multiply(ruralRatio.getValue());

        // 교육세 = (개별소비세*교육세율)
        Ratio eduRatio = getRatioByType(ratioMap, RatioTypeCd.EDU);
        BigDecimal edu = basisSales.multiply(eduRatio.getValue());

        // 부가세 = (과세가격+관세+개별소비세+교육세)*부가세율
        Ratio vatRatio = getRatioByType(ratioMap, RatioTypeCd.VAT);
        BigDecimal vat = (originAmount.add(customs).add(basisSales).add(rural).add(edu)).multiply(
                vatRatio.getValue());

        Ratio newCustomsRatio = customsRatio.withAmount(customs);
        Ratio newBasisSalesRatio = basisSalesRatio.withAmount(basisSales);
        Ratio newRuralRatio = ruralRatio.withAmount(rural);
        Ratio newEduRatio = eduRatio.withAmount(edu);
        Ratio newVatRatio = vatRatio.withAmount(vat);

        return product.withRatios(
                List.of(newCustomsRatio, newBasisSalesRatio, newRuralRatio, newEduRatio, newVatRatio));
    }
}
