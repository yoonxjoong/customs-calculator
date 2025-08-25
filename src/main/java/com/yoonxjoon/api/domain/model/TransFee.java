package com.yoonxjoon.api.domain.model;

import com.yoonxjoon.api.constant.CurUnitCd;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.With;

@With
@AllArgsConstructor
@Getter
@Builder
public class TransFee {
    private final BigDecimal amount;
    private final CurUnitCd curUnitCd;
}
