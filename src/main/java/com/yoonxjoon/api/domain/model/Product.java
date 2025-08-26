package com.yoonxjoon.api.domain.model;

import com.yoonxjoon.api.constant.CalculatorType;
import com.yoonxjoon.api.constant.CurUnitCd;
import com.yoonxjoon.api.constant.RatioTypeCd;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder=true)
@With
@Getter
@AllArgsConstructor
public class Product {

    @Getter
    private String id;
    @Getter
    private String categoryId;
    @Getter
    private String name;
    @Getter
    private CalculatorType calculatorType;

    // 불변성을 위해 final로 선언하고, 생성자를 통해 초기화
    private final List<Price> prices;
    private final List<Ratio> ratios;
    private final List<TransFee> transFees;

    // 리스트는 불변성을 위해 새로운 리스트를 반환
    public List<Price> getPrices() {
        return List.copyOf(prices);
    }

    public List<Ratio> getRatios() {
        return List.copyOf(ratios);
    }

    public List<TransFee> getTransFees() {
        return List.copyOf(transFees);
    }

    public BigDecimal getKrwPriceAmount() {
        return prices.stream()
                .filter(obj -> obj.getCurUnitCd() == CurUnitCd.KRW)
                .map(Price::getAmount)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("KRW에 해당하는 가격이 없습니다."));
    }

    public BigDecimal getKrwTransFeeAmount() {
        return transFees.stream()
                .filter(obj -> obj.getCurUnitCd() == CurUnitCd.KRW)
                .map(TransFee::getAmount)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("KRW에 해당하는 가격이 없습니다."));
    }

    // 과세가격 = 상품가격 + 운임료
    public BigDecimal getOriginAmount() {
        return this.getKrwPriceAmount().add(this.getKrwTransFeeAmount());
    }

    public Map<RatioTypeCd, Ratio> getRatioMap() {
        return ratios.stream().collect(Collectors.toMap(Ratio::getRatioTypeCd,
                ratio -> ratio,
                (existing, replacement) -> existing)
        );
    }
}
