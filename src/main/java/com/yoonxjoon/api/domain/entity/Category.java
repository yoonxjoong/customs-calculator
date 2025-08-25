package com.yoonxjoon.api.domain.entity;

import com.yoonxjoon.api.constant.CalculatorType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categoies")
public class Category {
    @Id
    @Column(name = "category_id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name ="calculator_type")
    private CalculatorType calculatorType;
}
