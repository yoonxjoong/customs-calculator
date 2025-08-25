package com.yoonxjoon.api.domain.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TaxCalculationResponse {
    private String productId;
    private String categoryId;
    private String tax;
}
