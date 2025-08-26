package com.yoonxjoon.api.service;

import com.yoonxjoon.api.domain.model.Product;

public interface TaxCalculator {
    Product calculate(Product product);
}
