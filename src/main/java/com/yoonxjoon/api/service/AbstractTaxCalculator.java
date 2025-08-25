package com.yoonxjoon.api.service;

import com.yoonxjoon.api.constant.RatioTypeCd;
import com.yoonxjoon.api.domain.model.Ratio;
import java.util.Map;
import java.util.NoSuchElementException;

public abstract class AbstractTaxCalculator implements TaxCalculator {
    protected Ratio getRatioByType(Map<RatioTypeCd, Ratio> ratioMap, RatioTypeCd type) {
        Ratio ratio = ratioMap.getOrDefault(type, null);
        if (ratio == null) {
            throw new NoSuchElementException("해당 상품에 대한 " + type.getValue() + " 세율이 없습니다.");
        }
        return ratio;
    }
}
