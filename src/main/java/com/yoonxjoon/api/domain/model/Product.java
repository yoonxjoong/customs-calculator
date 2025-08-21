package com.yoonxjoon.api.domain.model;

import com.yoonxjoon.api.constant.CurUnitCd;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
public class Product {

    @Getter
    private String id;
    @Getter
    private String categoryId;
    @Getter
    private String name;
    @Getter
    private BigDecimal transFee;

    // 불변성을 위해 final로 선언하고, 생성자를 통해 초기화
    private final List<Price> prices;
    private final List<Ratio> ratios;

    public Product(String id, String categoryId, String name, BigDecimal transFee,
            List<Price> prices, List<Ratio> ratios) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.transFee = transFee;
        this.prices = prices;
        this.ratios = ratios;
    }

    // 리스트는 불변성을 위해 새로운 리스트를 반환
    public List<Price> getPrices() {
        return List.copyOf(prices);
    }

    public List<Ratio> getRatios() {
        return List.copyOf(ratios);
    }



}
