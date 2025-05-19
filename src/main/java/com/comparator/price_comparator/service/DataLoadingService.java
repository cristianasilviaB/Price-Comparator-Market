package com.comparator.price_comparator.service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.comparator.price_comparator.model.Discount;
import com.comparator.price_comparator.model.Product;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class DataLoadingService {

 private static final Logger logger = LoggerFactory.getLogger(DataLoadingService.class);   

 public List<Product> loadProductsFromCsv(String filePath, String storeName) {
 List<Product> products = new ArrayList<>();
 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
 LocalDate fileDate = extractDateFromFilename(filePath);

 try (Reader reader = new FileReader(filePath)) {
 CSVFormat csvFormat = CSVFormat.Builder.create()
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
 product.setDate(fileDate);

 logger.info("Record: {}", record.toMap());
 products.add(product);
 }
 } catch (IOException e) {
 log.error("Error loading products from CSV: {}", e.getMessage());
 }
 return products;
 }

 private LocalDate extractDateFromFilename(String filePath) {
  try {
  String filename = filePath.substring(filePath.lastIndexOf('/') + 1); // Extrage numele fișierului
  String datePart = filename.substring(filename.indexOf('_') + 1, filename.lastIndexOf('.')); // Izolează data
  DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  return LocalDate.parse(datePart, dateFormatter);
  } catch (Exception e) {
  log.error("Error extracting date from filename: {}", e.getMessage());
  return null; // Sau o altă valoare implicită, după caz
  }
  }
  
public List<Discount> loadDiscountsFromCsv(String filePath, String storeName) {
 List<Discount> discounts = new ArrayList<>();
 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
 try (Reader reader = new FileReader(filePath)) {
 CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
 .setHeader()
 .setSkipHeaderRecord(true)
 .setDelimiter(';')
 .setTrim(true)
 .build();
 Iterable<CSVRecord> records = csvFormat.parse(reader);
 for (CSVRecord record : records) {
 Discount discount = new Discount();
 discount.setProductId(record.get("product_id"));
 discount.setProductName(record.get("product_name"));
 discount.setBrand(record.get("brand"));
 discount.setPackageQuantity(Double.parseDouble(record.get("package_quantity")));
 discount.setPackageUnit(record.get("package_unit"));
 discount.setProductCategory(record.get("product_category"));
 discount.setFromDate(LocalDate.parse(record.get("from_date"), dateFormatter));
 discount.setToDate(LocalDate.parse(record.get("to_date"), dateFormatter));
 discount.setPercentageOfDiscount(Double.parseDouble(record.get("percentage_of_discount")));
 discount.setStoreName(storeName);
 discounts.add(discount);
 }
 } catch (IOException e) {
 log.error("Error loading discounts from CSV: {}", e.getMessage());
 }
 return discounts;
 }
}