package com.yoonxjoon.api.domain.model;

import com.yoonxjoon.api.constant.RatioTypeCd;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.With;

@With
@Builder
public class Ratio {
    @Getter
    private final RatioTypeCd ratioTypeCd;
    @Getter
    private final BigDecimal value;
    @Getter
    private final BigDecimal amount;
}