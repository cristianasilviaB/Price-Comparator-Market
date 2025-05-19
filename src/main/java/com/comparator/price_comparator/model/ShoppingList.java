package com.comparator.price_comparator.model;

import java.util.List;

import lombok.Data;

@Data
public class ShoppingList {
 private String storeName;
 private List<BasketItem> items;
 private double totalCost;
}