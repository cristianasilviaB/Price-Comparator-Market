package com.comparator.price_comparator.model;
import lombok.Data;

 @Data
 public class PriceAlert {
  private String alertId; // Use String for simplicity
  private String productId;
  private Double targetPrice;
  private String storeName;
  private boolean emailSent;
 }
