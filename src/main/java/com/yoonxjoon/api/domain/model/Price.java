package com.yoonxjoon.api.domain.model;

import com.yoonxjoon.api.constant.CurUnitCd;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Price {
    // Getter 메서드
    private final BigDecimal amount;
    private final CurUnitCd curUnitCd;

    public Price(BigDecimal amount, CurUnitCd curUnitCd) {
        this.amount = amount;
        this.curUnitCd = curUnitCd;
    }

}