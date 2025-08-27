package com.yoonxjoon.api.constant;

public enum CalculatorType {
    CALCULATOR_CODE_NULL(null),
    CALCULATOR_CODE_1("taxCalculator1"), // 관세, 부가세
    CALCULATOR_CODE_2("taxCalculator2"), // 부가세
    CALCULATOR_CODE_3("taxCalculator3"), // 관세, 주세, 교육세, 부가세
    CALCULATOR_CODE_4("taxCalculator4"), // 주세, 교육세, 부가세
    CALCULATOR_CODE_5("taxCalculator5"), // 주세, 교육세
    CALCULATOR_CODE_6("taxCalculator6"), // 관세, 개별소비세, 교육세, 부가세
    CALCULATOR_CODE_7("taxCalculator7"), // 관세,개별소비세, 농특세, 교육세, 부가세
    CALCULATOR_CODE_8("taxCalculator8"), // 관세, 개별소비세, 부가세
    CALCULATOR_CODE_9("9"), // 관세
    CALCULATOR_CODE_10("10"), // 미발생
    CALCULATOR_CODE_11("11"), // 주세(용량*리터당 주세금액), 교육세
    CALCULATOR_CODE_12("12"), // 관세, 주세(용량*리터당 주세금액), 교육세, 부가세
    CALCULATOR_CODE_13("13"), // 관세, 주세(용량*리터당 주세금액), 교육세, 부가세
    CALCULATOR_CODE_14("14"), // 관세, 주세(용량*리터당 주세금액), 교육세, 부가세
    CALCULATOR_CODE_15("15"), // 관세, 주세(용량*리터당 주세금액), 교육세, 부가세

    ;

    private final String type;

    CalculatorType(String type) {
        this.type = type;
    }

    public String getValue() {
        return type;
    }
}
