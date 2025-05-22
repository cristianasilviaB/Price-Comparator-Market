package com.comparator.price_comparator.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comparator.price_comparator.model.Discount;
import com.comparator.price_comparator.model.PriceRecord;
import com.comparator.price_comparator.model.Product;
import com.comparator.price_comparator.repository.ProductRepository;
@Service
public class PricingService {

    private static final Logger logger = LoggerFactory.getLogger(PricingService.class);

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

 public List<PriceRecord> getPriceHistory(String productId, String storeName, String brand, String category) {
  List<Product> allProducts = productRepository.getAllProducts();

  return allProducts.stream()
  .filter(p -> productId == null || p.getProductId().equalsIgnoreCase(productId))
  .filter(p -> storeName == null || p.getStoreName().equalsIgnoreCase(storeName))
  .filter(p -> brand == null || p.getBrand().equalsIgnoreCase(brand))
  .filter(p -> category == null || p.getProductCategory().equalsIgnoreCase(category))
  .map(p -> {
  PriceRecord record = new PriceRecord();
  record.setProductId(p.getProductId());
  record.setProductName(p.getProductName());
  record.setBrand(p.getBrand());
  record.setStoreName(p.getStoreName());
  record.setProductCategory(p.getProductCategory());
  record.setDate(p.getDate()); // Folosește p.getDate()
  record.setOriginalPrice(p.getPrice());
  record.setDiscountedPrice(p.getDiscountedPrice());
  record.setDiscountPercentage(p.getCurrentDiscountPercentage());
  return record;
  })
  .collect(Collectors.toList());
 }
  //method to calculate value per unit
  private Product calculateValuePerUnit(Product product) {
  logger.info("ENTERING calculateValuePerUnit for product: {}", product.getProductId());
  if (product.getPackageQuantity() != 0) {
  double price = product.getPrice();
  double packageQuantity = product.getPackageQuantity();
  double valuePerUnit = price / packageQuantity;
  logger.info("Product ID: {}, Price: {}, Package Quantity: {}, Value Per Unit: {}",
  product.getProductId(), price, packageQuantity, valuePerUnit); 
  product.setValuePerUnit(valuePerUnit);
  } else {
  product.setValuePerUnit(null); // Handle zero quantity
  }
  logger.info("EXITING calculateValuePerUnit for product: {}", product.getProductId()); 
  return product;
 }
  public List<Product> getRecommendedProducts(String productId) {
  logger.info("Getting recommendations for product ID: {}", productId);
  Product baseProduct = productRepository.getProductById(productId);
  if (baseProduct == null) {
  logger.warn("Base product not found for ID: {}", productId);
  return Collections.emptyList();
  }
  String category = baseProduct.getProductCategory();
  logger.info("Base product category: {}", category);
  List<Product> allProducts = getProductsByCategory(category); // get from the service layer
  logger.info("Number of products in category {}: {}", category, allProducts.size());
  List<Product> recommendations = allProducts.stream()
  .filter(p -> !p.getProductId().equals(productId))
  .limit(5)
  .collect(Collectors.toList());
  logger.info("Number of recommendations: {}", recommendations.size());
  return recommendations;
 }

 public Product getProductById(String productId) {
  // Iterate over all products.
  List<Product> allProducts = productRepository.getAllProducts();
  for (Product product : allProducts) {
  if (product.getProductId().equals(productId)) {
  return product;
  }
  }
  return null; // Product not found
 }
}