// src/main/java/com/comparator/price_comparator/PriceComparatorApplication.java
package com.comparator.price_comparator;

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
 String lidlProductsFile1 = "src/main/resources/data/lidl_2025-05-01.csv";
 String lidlProductsFile2 = "src/main/resources/data/lidl_2025-05-08.csv";
 String profiProductsFile1 = "src/main/resources/data/profi_2025-05-01.csv";
 String profiProductsFile2 = "src/main/resources/data/profi_2025-05-08.csv";
 String kauflandProductsFile1 = "src/main/resources/data/kaufland_2025-05-01.csv";
 String kauflandProductsFile2 = "src/main/resources/data/kaufland_2025-05-08.csv";
 String lidlDiscountsFile1 = "src/main/resources/data/lidl_discounts_2025-05-01.csv";
 String lidlDiscountsFile2 = "src/main/resources/data/lidl_discounts_2025-05-08.csv";
 String profiDiscountsFile1 = "src/main/resources/data/profi_discounts_2025-05-01.csv";
 String profiDiscountsFile2 = "src/main/resources/data/profi_discounts_2025-05-081.csv";
 String kauflandDiscountsFile1 = "src/main/resources/data/kaufland_discounts_2025-05-01.csv";
 String kauflandDiscountsFile2 = "src/main/resources/data/kaufland_discounts_2025-05-08.csv";

 productRepository.addProducts(dataLoadingService.loadProductsFromCsv(lidlProductsFile1, "Lidl"));
 productRepository.addProducts(dataLoadingService.loadProductsFromCsv(lidlProductsFile2, "Lidl"));
 productRepository.addProducts(dataLoadingService.loadProductsFromCsv(profiProductsFile1, "Profi"));
 productRepository.addProducts(dataLoadingService.loadProductsFromCsv(profiProductsFile2, "Profi"));
 productRepository.addProducts(dataLoadingService.loadProductsFromCsv(kauflandProductsFile1, "Kaufland"));
 productRepository.addProducts(dataLoadingService.loadProductsFromCsv(kauflandProductsFile2, "Kaufland"));

 // Load discounts using the new method
 List<Discount> lidlDiscounts1 = dataLoadingService.loadDiscountsFromCsv(lidlDiscountsFile1, "Lidl");
 discountRepository.addDiscounts(lidlDiscounts1);
 List<Discount> lidlDiscounts2 = dataLoadingService.loadDiscountsFromCsv(lidlDiscountsFile2, "Lidl");
 discountRepository.addDiscounts(lidlDiscounts2);
 List<Discount> profiDiscounts1 = dataLoadingService.loadDiscountsFromCsv(profiDiscountsFile1, "Profi");
 discountRepository.addDiscounts(profiDiscounts1);
 List<Discount> profiDiscounts2 = dataLoadingService.loadDiscountsFromCsv(profiDiscountsFile2, "Profi");
 discountRepository.addDiscounts(profiDiscounts2);
 List<Discount> kauflandDiscounts1 = dataLoadingService.loadDiscountsFromCsv(kauflandDiscountsFile1, "Kaufland");
 discountRepository.addDiscounts(kauflandDiscounts1);
 List<Discount> kauflandDiscounts2 = dataLoadingService.loadDiscountsFromCsv(kauflandDiscountsFile2, "Kaufland");
 discountRepository.addDiscounts(kauflandDiscounts2);

    // Apply discounts to products
 //LocalDate currentDate = LocalDate.now();
 //productRepository.applyDiscounts(currentDate);

 // Add log statement to verify data loading
 logger.info("Loaded {} products from CSV", productRepository.getAllProducts().size());
 logger.info("Loaded {} discounts from CSV", discountRepository.getAllDiscounts().size());
 }
}