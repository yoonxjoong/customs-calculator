package com.yoonxjoon.api.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "categoies")
public class CategoryEntity {
    @Id
    @Column(name = "category_id")
    private String id;

    @Column(name = "name")
    private String name;
}
