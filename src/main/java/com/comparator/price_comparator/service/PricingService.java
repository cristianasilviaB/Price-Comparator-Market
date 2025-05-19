package com.comparator.price_comparator.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comparator.price_comparator.model.Discount;
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
  
  
  public List<Product> getProductsWithHighestCurrentDiscounts(LocalDate currentDate) {
  List<Product> allProducts = productRepository.getAllProducts();

  // Apply discounts and calculate discounted prices
  List<Product> productsWithDiscounts = allProducts.stream().map(product -> {
  List<Discount> discounts = productRepository.findDiscountsByProductIdAndStoreName(
  product.getProductId(), product.getStoreName());

  // Find the highest discount for the product
  Discount highestDiscount = discounts.stream()
  .filter(discount -> !currentDate.isBefore(discount.getFromDate())
  && !currentDate.isAfter(discount.getToDate()))
  .max(Comparator.comparing(Discount::getPercentageOfDiscount)).orElse(null);

  if (highestDiscount != null) {
  // Calculate discounted price
  double discountPercentage = highestDiscount.getPercentageOfDiscount();
  double discountedPrice = product.getPrice() * (1 - (discountPercentage / 100.0));
  product.setDiscountedPrice(discountedPrice);
  product.setCurrentDiscountPercentage(discountPercentage);
  } else {
  // No discount found, set discounted price to null
  product.setDiscountedPrice(null);
  product.setCurrentDiscountPercentage(null);
  }
  return product;
  }).collect(Collectors.toList());

  // Filter out products with no discounts and sort by discount percentage
  List<Product> bestDiscounts = productsWithDiscounts.stream()
  .filter(product -> product.getDiscountedPrice() != null)
  .sorted(Comparator.comparing(Product::getCurrentDiscountPercentage).reversed())
  .collect(Collectors.toList());

  return bestDiscounts;
 }
  
}