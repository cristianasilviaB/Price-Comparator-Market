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

  private Double discountedPrice;
  private Double currentDiscountPercentage;

  public Double getDiscountedPrice() {
  return discountedPrice;
  }
  public void setDiscountedPrice(Double discountedPrice) {
  this.discountedPrice = discountedPrice;
  }
  // Getter and setter for currentDiscountPercentage
  public Double getCurrentDiscountPercentage() {
  return currentDiscountPercentage;
  }
  public void setCurrentDiscountPercentage(Double currentDiscountPercentage) {
  this.currentDiscountPercentage = currentDiscountPercentage;
  }
}