package com.yoonxjoon.api.service;

import com.yoonxjoon.api.domain.model.Product;

public interface TaxCalculator {
    public Product calculate(Product product);
}
