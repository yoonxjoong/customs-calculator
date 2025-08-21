package com.yoonxjoon.api.constant;

import java.util.Arrays;

public enum CurUnitCd {
    KRW("KRW"),
    USD("USD");

    private String value;

    CurUnitCd(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    public static CurUnitCd fromString(String text) {
        if (text == null) {
            throw new IllegalArgumentException("No Text value");
        }
        return Arrays.stream(CurUnitCd.values())
                .filter(unit -> unit.value.equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No enum constant for value: " + text));
    }
}
