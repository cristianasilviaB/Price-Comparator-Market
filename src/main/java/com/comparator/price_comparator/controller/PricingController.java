package com.comparator.price_comparator.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comparator.price_comparator.model.Product;
import com.comparator.price_comparator.service.PricingService;

@RestController
public class PricingController {
 @Autowired
 private PricingService pricingService;
 @GetMapping("/products")
  public ResponseEntity<List<Product>> getAllProducts() {
  List<Product> products = pricingService.getAllProducts();
  return ResponseEntity.ok(products);
  }

@GetMapping("/products/category")
 public ResponseEntity<List<Product>> getProductsByCategory(@RequestParam String category) {
 List<Product> products = pricingService.getProductsByCategory(category);
 if (products.isEmpty()) {
  return ResponseEntity.notFound().build(); // Return 404 if no products found
  }
  return ResponseEntity.ok(products);
}

@GetMapping("/products/search")
  public ResponseEntity<List<Product>> searchProducts(@RequestParam String query) {
  List<Product> products = pricingService.searchProducts(query);
  if (products.isEmpty()) {
  return ResponseEntity.notFound().build();
  }
  return ResponseEntity.ok(products);
  }

@GetMapping("/products/best-discounts")
    public ResponseEntity<List<Product>> getProductsWithHighestCurrentDiscounts(
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LocalDate currentDate = (date != null) ? date : LocalDate.now();
        List<Product> list = pricingService.getProductsWithHighestCurrentDiscounts(currentDate);
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
         return ResponseEntity.ok(list);
    }
}