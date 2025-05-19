package com.comparator.price_comparator.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.comparator.price_comparator.model.Discount;
import com.comparator.price_comparator.model.Product;

@Repository
public class ProductRepository {

private final List<Product> products = new ArrayList<>();

@Autowired
private DiscountRepository discountRepository;

public void addProducts(List<Product> products) {
this.products.addAll(products);
}

public List<Product> getAllProducts() {
return new ArrayList<>(products);
}

public void addDiscounts(List<Discount> discounts) {
discountRepository.addDiscounts(discounts);
}

public List<Discount> getAllDiscounts() {
        return discountRepository.getAllDiscounts();
    }

public List<Product> getProductsByCategory(String category) {
return products.stream()
.filter(p -> p.getProductCategory().equalsIgnoreCase(category))
.collect(Collectors.toList());
}

public List<Product> applyDiscounts(LocalDate currentDate) {
  return products.stream()
  .map(product -> {
  discountRepository.findDiscountsByProductIdAndStoreName(product.getProductId(), product.getStoreName())
  .stream()
  .filter(discount -> !currentDate.isBefore(discount.getFromDate()) && !currentDate.isAfter(discount.getToDate()))
  .findFirst()
  .ifPresent(discount -> {
  double discountPercentage = discount.getPercentageOfDiscount();
  double discountedPrice = product.getPrice() * (1 - (discountPercentage / 100));
  product.setPrice(discountedPrice);
  });
  return product;
  })
  .collect(Collectors.toList());
 }
 
public List<Discount> findDiscountsByProductIdAndStoreName(String productId, String storeName) {
    return discountRepository.findDiscountsByProductIdAndStoreName(productId, storeName);
}

public Product getProductById(String productId, String storeName) {
  for (Product product : products) {
  if (product.getProductId().equals(productId) && product.getStoreName().equals(storeName)) {
  return product;
  }
  }
  return null;
}
}