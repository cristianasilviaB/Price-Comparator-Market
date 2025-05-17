// src/main/java/com/pricecomparator/service/DataLoadingService.java
package com.comparator.price_comparator.service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import com.comparator.price_comparator.model.Product;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DataLoadingService {

 public List<Product> loadProductsFromCsv(String filePath, String storeName) {
 List<Product> products = new ArrayList<>();
 try (Reader reader = new FileReader(filePath)) {
 CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
 .setHeader()
 .setSkipHeaderRecord(true)
 .setDelimiter(';')
 .setTrim(true) // Add this line to trim headers
 .build();

 Iterable<CSVRecord> records = csvFormat.parse(reader);
 
 for (CSVRecord record : records) {
 log.info("Record: {}", record.toMap());
 Product product = new Product();
 product.setProductId(record.get("product_id"));
 product.setProductName(record.get("product_name"));
 product.setProductCategory(record.get("product_category"));
 product.setBrand(record.get("brand"));
 product.setPackageQuantity(Double.parseDouble(record.get("package_quantity")));
 product.setPackageUnit(record.get("package_unit"));
 product.setPrice(Double.parseDouble(record.get("price")));
 product.setCurrency(record.get("currency"));
 product.setStoreName(storeName);
 products.add(product);
 }
 } catch (IOException e) {
 log.error("Error loading products from CSV: {}", e.getMessage());
 }
 return products;
 }
}