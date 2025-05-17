package com.comparator.price_comparator.model;

import lombok.Data;

@Data
public class Product {
 private String productId;
 private String productName;
 private String productCategory;
 private String brand;
 private double packageQuantity;
 private String packageUnit;
 private double price;
 private String currency;
 private String storeName;
}