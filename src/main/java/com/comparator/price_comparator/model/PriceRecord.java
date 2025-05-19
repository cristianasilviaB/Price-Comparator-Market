package com.comparator.price_comparator.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PriceRecord {
    private String productId;
    private String productName;
    private String brand;
    private String storeName;
    private String productCategory;
    private LocalDate date;
    private double originalPrice;
    private Double discountedPrice;
    private Double discountPercentage;
}
