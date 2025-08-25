package com.yoonxjoon.api.controller;

import com.yoonxjoon.api.dto.TaxCalculationRequest;
import com.yoonxjoon.api.dto.TaxCalculationResponse;
import com.yoonxjoon.api.service.TaxCalculatorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class TaxCalculatorController {

    private final TaxCalculatorService taxCalculatorService;

    @PostMapping("/test1")
    public TaxCalculationResponse selectMemberInfo(@RequestBody TaxCalculationRequest request) {

        return taxCalculatorService.calculate(request);
    }
}
