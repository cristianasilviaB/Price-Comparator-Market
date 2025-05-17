package com.comparator.price_comparator.service;

import java.util.List;

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
 return productRepository.getProductsByCategory(category);
 }
}