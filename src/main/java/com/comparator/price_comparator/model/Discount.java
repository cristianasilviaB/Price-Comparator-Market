package com.comparator.price_comparator.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Discount {
 private String productId;
 private String productName;
 private String brand;
 private double packageQuantity;
 private String packageUnit;
 private String productCategory;
 private LocalDate fromDate;
 private LocalDate toDate;
 private double percentageOfDiscount;
 private String storeName;
}