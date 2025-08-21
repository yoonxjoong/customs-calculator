package com.yoonxjoon.api.constant;

public enum RatioTypeCd {
    /**
     * 관세
     */
    CUSTOMS("CUSTOMS"),

    /**
     * 부가세
     */
    VAT("VAT"),

    /**
     * 특별 소비세
     */
    SPCL_SALES("SPCL_SALES"),

    /**
     * 기본 소비세
     */
    BASE_SALES("BASE_SALES"),

    /**
     * 교육세
     */
    EDU("EDU"),

    /**
     * 농특세
     */
    RURAL("RURAL"),

    /**
     * 주류세
     */
    LIQUOR("LIQUOR");

    private final String type;

    RatioTypeCd(String customsTax) {
        this.type = customsTax;
    }

    public String getValue() {
        return type;
    }
}
