package com.comparator.price_comparator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comparator.price_comparator.model.Product;
import com.comparator.price_comparator.service.PricingService;
@RestController
public class PricingController {
 @Autowired
 private PricingService pricingService;
 @GetMapping("/products")
 public List<Product> getAllProducts() {
 return pricingService.getAllProducts();
 }
}