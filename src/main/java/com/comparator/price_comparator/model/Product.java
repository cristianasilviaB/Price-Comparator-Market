package com.comparator.price_comparator.model;

import java.time.LocalDate;

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

 private LocalDate date;

  private Double discountedPrice;
  private Double currentDiscountPercentage;

  private transient Double valuePerUnit; // Calculated value per unit

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

  public Double getValuePerUnit() {
  return valuePerUnit;
  }
  public void setValuePerUnit(Double valuePerUnit) {
  this.valuePerUnit = valuePerUnit;
  }
  
}