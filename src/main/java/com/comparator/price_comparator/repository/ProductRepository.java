// src/main/java/com/pricecomparator/repository/ProductRepository.java
package com.comparator.price_comparator.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.comparator.price_comparator.model.Product;

@Repository
public class ProductRepository {
 private final List<Product> products = new ArrayList<>();

 public void addProducts(List<Product> products) {
 this.products.addAll(products);
 }

 public List<Product> getAllProducts() {
 return new ArrayList<>(products); // Return a copy to prevent modification
 }
}