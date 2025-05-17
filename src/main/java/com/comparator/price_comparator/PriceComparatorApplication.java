// src/main/java/com/comparator/price_comparator/PriceComparatorApplication.java
package com.comparator.price_comparator;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.comparator.price_comparator.model.Discount;
import com.comparator.price_comparator.repository.DiscountRepository;
import com.comparator.price_comparator.repository.ProductRepository;
import com.comparator.price_comparator.service.DataLoadingService;

@SpringBootApplication
public class PriceComparatorApplication implements CommandLineRunner {

 private static final Logger logger = LoggerFactory.getLogger(PriceComparatorApplication.class);
 @Autowired
 private DataLoadingService dataLoadingService;
 @Autowired
 private ProductRepository productRepository;
 @Autowired
private DiscountRepository discountRepository;

 public static void main(String[] args) {
 SpringApplication.run(PriceComparatorApplication.class, args);
 }

 @Override
 public void run(String... args) throws Exception {
 // Load data from CSV files on startup
 String lidlProductsFile = "src/main/resources/data/lidl_2025-05-01.csv";
 String profiProductsFile = "src/main/resources/data/profi_2025-05-01.csv";
 String lidlDiscountsFile = "src/main/resources/data/lidl_discounts_2025-05-01.csv";

 productRepository.addProducts(dataLoadingService.loadProductsFromCsv(lidlProductsFile, "Lidl"));
 productRepository.addProducts(dataLoadingService.loadProductsFromCsv(profiProductsFile, "Profi"));
 discountRepository.addDiscounts(dataLoadingService.loadDiscountsFromCsv(lidlDiscountsFile,  "Lidl"));


 // Load discounts using the new method
 List<Discount> discounts = dataLoadingService.loadDiscountsFromCsv(lidlDiscountsFile, "Lidl");
 productRepository.addDiscounts(discounts);

    // Apply discounts to products
 LocalDate currentDate = LocalDate.now();
 productRepository.applyDiscounts(currentDate);

 // Add log statement to verify data loading
 logger.info("Loaded {} products from CSV", productRepository.getAllProducts().size());
 }
}