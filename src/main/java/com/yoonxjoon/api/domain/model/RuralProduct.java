package com.yoonxjoon.api.domain.model;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class RuralProduct extends Product{
    // 기준초과 금액
    private BigDecimal basisAboveAmount;
}
