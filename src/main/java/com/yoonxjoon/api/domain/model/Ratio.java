package com.yoonxjoon.api.domain.model;

import com.yoonxjoon.api.constant.RatioTypeCd;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.With;

@Getter
@With
@Builder
public class Ratio {
    private final RatioTypeCd ratioTypeCd;
    private final BigDecimal value;
    private final BigDecimal amount;
}