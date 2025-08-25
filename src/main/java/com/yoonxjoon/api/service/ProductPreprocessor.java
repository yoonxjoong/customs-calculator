package com.yoonxjoon.api.service;

import com.yoonxjoon.api.constant.CurUnitCd;
import com.yoonxjoon.api.constant.RatioTypeCd;
import com.yoonxjoon.api.domain.model.Price;
import com.yoonxjoon.api.domain.model.Product;
import com.yoonxjoon.api.domain.model.Ratio;
import com.yoonxjoon.api.domain.model.TransFee;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ProductPreprocessor {
    public List<Ratio> addTaxRatio(Product originalProduct) {
        List<Ratio> newRatio = new ArrayList<>();

        newRatio.add(Ratio.builder()
                .ratioTypeCd(RatioTypeCd.CUSTOMS)
                .value(new BigDecimal("0.5"))
                .build());

        newRatio.add(Ratio.builder()
                .ratioTypeCd(RatioTypeCd.VAT)
                .value(new BigDecimal("0.1"))
                .build());

        newRatio.add(Ratio.builder()
                .ratioTypeCd(RatioTypeCd.SPCL_SALES)
                .value(new BigDecimal("0.1"))
                .build());

        newRatio.add(Ratio.builder()
                .ratioTypeCd(RatioTypeCd.BASE_SALES)
                .value(new BigDecimal("0.4"))
                .build());

        newRatio.add(Ratio.builder()
                .ratioTypeCd(RatioTypeCd.EDU)
                .value(new BigDecimal("0.6"))
                .build());

        newRatio.add(Ratio.builder()
                .ratioTypeCd(RatioTypeCd.RURAL)
                .value(new BigDecimal("0.7"))
                .build());

        newRatio.add(Ratio.builder()
                .ratioTypeCd(RatioTypeCd.LIQUOR)
                .value(new BigDecimal("0.8"))
                .build());

        return newRatio;
    }

    public List<Price> addExchangedPrices(List<Price> prices) {
        // 기존 prices 리스트를 복사하여 새로운 리스트를 만듭니다.
        List<Price> newPrices = new ArrayList<>(prices);

        Price sourcePrice = newPrices.get(0);
        if (sourcePrice == null) {
            return newPrices;
        }

        if (!sourcePrice.getCurUnitCd().equals(CurUnitCd.USD)) {
            Price usdPrice = Price.builder()
                    .amount(sourcePrice.getAmount().multiply(BigDecimal.ONE))
                    .curUnitCd(CurUnitCd.USD)
                    .build();
            newPrices.add(usdPrice);
        }

        Price targetPrice = Price.builder()
                .amount(sourcePrice.getAmount().multiply(BigDecimal.ONE))
                .curUnitCd(CurUnitCd.KRW)
                .build();
        newPrices.add(targetPrice);

        return List.copyOf(newPrices);
    }

    public List<TransFee> addTransFee(List<TransFee> transFees) {
        List<TransFee> newTransFees = new ArrayList<>(transFees);

        TransFee sourceTransFee = newTransFees.get(0);
        if(sourceTransFee == null) {
            return newTransFees;
        }

        if(!sourceTransFee.getCurUnitCd().equals(CurUnitCd.USD)) {
            TransFee usdTransFee = TransFee.builder()
                    .amount(sourceTransFee.getAmount().multiply(BigDecimal.ONE))
                    .curUnitCd(CurUnitCd.USD)
                    .build();

            newTransFees.add(usdTransFee);
        }

        TransFee targetTransFee = TransFee.builder()
                .amount(sourceTransFee.getAmount().multiply(BigDecimal.ONE))
                .curUnitCd(CurUnitCd.KRW)
                .build();

        newTransFees.add(targetTransFee);

        return List.copyOf(newTransFees);
    }

}
