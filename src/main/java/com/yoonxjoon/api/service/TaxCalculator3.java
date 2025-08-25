package com.yoonxjoon.api.service;

import com.yoonxjoon.api.constant.RatioTypeCd;
import com.yoonxjoon.api.domain.model.Product;
import com.yoonxjoon.api.domain.model.Ratio;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

// 관세, 주세, 교육세, 부가세
// 관세[과세가격*관세율]+주세[(과세가격+관세)*주세율]+교육세[주세*교육세율]+부가세[(과세가격+관세+주세+교육세)*부가세율]
@Component
@Slf4j
public class TaxCalculator3 extends AbstractTaxCalculator {

    @Override
    public Product calculate(Product product) {
        BigDecimal originAmount = product.getOriginAmount();

        Map<RatioTypeCd, Ratio> ratioMap = product.getRatioMap();

        // 관세 = (과세가격 * 관세율)
        Ratio customsRatio = getRatioByType(ratioMap, RatioTypeCd.CUSTOMS);
        BigDecimal customs = originAmount.multiply(customsRatio.getValue());

        // 주세 = (과세가격+관세)*주세율
        Ratio liquorRatio = getRatioByType(ratioMap, RatioTypeCd.LIQUOR);
        BigDecimal liquor = (originAmount.add(customs)).multiply(liquorRatio.getValue());

        // 교육세[주세*교육세율]
        Ratio eduRatio = getRatioByType(ratioMap, RatioTypeCd.EDU);
        BigDecimal edu = liquor.multiply(eduRatio.getValue());

        // 부가세 = 과세가격 * 부가세율
        Ratio vatRatio = getRatioByType(ratioMap, RatioTypeCd.VAT);
        BigDecimal vat = (originAmount.add(customs).add(liquor).add(edu)).multiply(
                vatRatio.getValue());

        Ratio newCustomsRatio = customsRatio.withAmount(customs);
        Ratio newLiquorRatio = customsRatio.withAmount(liquor);
        Ratio newEduRatio = customsRatio.withAmount(edu);
        Ratio newVatRatio = vatRatio.withAmount(vat);

        return product.withRatios(
                List.of(newCustomsRatio, newLiquorRatio, newEduRatio, newVatRatio));

    }
}
