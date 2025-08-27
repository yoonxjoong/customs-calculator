package com.yoonxjoon.api.domain.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxCalculationRequest {
    private String productId;

    private String categoryId;

    private BigDecimal transFee;

    private String transFeeUnitCd;

    private BigDecimal price;

    private String curUnitCd;
}
