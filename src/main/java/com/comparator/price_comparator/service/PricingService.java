package com.comparator.price_comparator.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comparator.price_comparator.model.Product;
import com.comparator.price_comparator.repository.ProductRepository;

@Service
public class PricingService {

 @Autowired
 private ProductRepository productRepository;

 public List<Product> getAllProducts() {
 return productRepository.getAllProducts();
 }

 public List<Product> getProductsByCategory(String category) {
 if (category == null || category.isBlank()) {
  return List.of();
  }
  return productRepository.getProductsByCategory(category.toLowerCase().trim());
  }

   public List<Product> searchProducts(String query) {
  if (query == null || query.isBlank()) {
  return List.of();
  }
  String q = query.toLowerCase().trim();
  return productRepository.getAllProducts().stream().filter(p -> p.getProductName().toLowerCase().contains(q)
  || p.getBrand().toLowerCase().contains(q)).collect(Collectors.toList());
  }

  
  
}