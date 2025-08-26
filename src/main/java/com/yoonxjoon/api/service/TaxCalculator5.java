package com.yoonxjoon.api.service;

import com.yoonxjoon.api.constant.RatioTypeCd;
import com.yoonxjoon.api.domain.model.Product;
import com.yoonxjoon.api.domain.model.Ratio;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

// 주세, 교육세
// 주세[과세가격*주세율]+교육세[주세*교육세율]
@Component
@Slf4j
public class TaxCalculator5 extends AbstractTaxCalculator {

    @Override
    public Product calculate(Product product) {
        BigDecimal originAmount = product.getOriginAmount();

        Map<RatioTypeCd, Ratio> ratioMap = product.getRatioMap();

        // 주세 = 과세가격 * 주세율
        Ratio liquorRatio = getRatioByType(ratioMap, RatioTypeCd.LIQUOR);
        BigDecimal liquor = originAmount.multiply(liquorRatio.getValue());

        // 교육세[주세*교육세율]
        Ratio eduRatio = getRatioByType(ratioMap, RatioTypeCd.EDU);
        BigDecimal edu = liquor.multiply(eduRatio.getValue());

        Ratio newLiquorRatio = liquorRatio.withAmount(liquor);
        Ratio newEduRatio = eduRatio.withAmount(edu);

        return product.withRatios(List.of(newLiquorRatio, newEduRatio));

    }
}
