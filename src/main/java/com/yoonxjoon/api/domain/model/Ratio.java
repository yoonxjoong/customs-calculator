package com.yoonxjoon.api.domain.model;

import com.yoonxjoon.api.constant.RatioTypeCd;
import java.math.BigDecimal;

public class Ratio {
    private final RatioTypeCd ratioTypeCd;
    private final BigDecimal value;

    public Ratio(RatioTypeCd ratioTypeCd, BigDecimal value) {
        this.ratioTypeCd = ratioTypeCd;
        this.value = value;
    }

    public RatioTypeCd getRatioTypeCd() { return ratioTypeCd; }
    public BigDecimal getValue() { return value; }
}