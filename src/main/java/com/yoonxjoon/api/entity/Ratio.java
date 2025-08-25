package com.yoonxjoon.api.entity;

import com.yoonxjoon.api.constant.RatioTypeCd;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "ratios")
public class Ratio {
    @Id
    @Column(name = "ratio_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Ratio 자체의 기본키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(name = "ratio_type_cd")
    private RatioTypeCd ratioTypeCd;

    @Column(name = "value")
    private BigDecimal value;

}
