package com.yoonxjoon.api.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.yoonxjoon.api.constant.CurUnitCd;
import com.yoonxjoon.api.domain.model.Product;
import com.yoonxjoon.api.dto.TaxCalculationRequest;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaxCalculatorMapperTest {
    private TaxCalculatorMapper taxCalculatorMapper;

    @BeforeEach
    void setUp() {
        taxCalculatorMapper = new TaxCalculatorMapper();
    }

    @Test
    void toDomain_should_return_correct_product_when_valid_request_given() {
        // Given
        TaxCalculationRequest request = TaxCalculationRequest.builder()
                .productId("product-001")
                .categoryId("category-A")
                .transFee(BigDecimal.valueOf(10.50))
                .price(BigDecimal.valueOf(100.00))
                .curUnitCd("USD")
                .build();

        // When
        Product product = taxCalculatorMapper.toDomain(request);

        // Then
        assertThat(product).isNotNull();
        assertThat(product.getId()).isEqualTo("product-001");
        assertThat(product.getCategoryId()).isEqualTo("category-A");
        assertThat(product.getTransFee()).isEqualTo(BigDecimal.valueOf(10.50));

        assertThat(product.getPrices()).hasSize(1);
        assertThat(product.getPrices().get(0).getAmount()).isEqualTo(BigDecimal.valueOf(100.00));
        assertThat(product.getPrices().get(0).getCurUnitCd()).isEqualTo(CurUnitCd.USD);
    }

    @Test
    void toDomain_should_throw_exception_when_invalid_currency_code_given() {
        // Given
        TaxCalculationRequest request = TaxCalculationRequest.builder()
                .productId("product-002")
                .categoryId("category-B")
                .price(BigDecimal.valueOf(50.00))
                .curUnitCd("EUR") // 존재하지 않는 통화 코드
                .build();

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> taxCalculatorMapper.toDomain(request));
    }
}