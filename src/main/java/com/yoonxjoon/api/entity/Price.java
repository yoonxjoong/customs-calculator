package com.yoonxjoon.api.entity;

import com.yoonxjoon.api.constant.CurUnitCd;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "prices")
public class Price {
    @Id
    @Column(name = "price_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "amount")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "cur_unit_cd")
    private CurUnitCd curUnitCd;
}
