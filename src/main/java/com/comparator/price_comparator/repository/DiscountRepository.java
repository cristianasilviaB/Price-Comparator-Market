package com.comparator.price_comparator.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.comparator.price_comparator.model.Discount;

@Repository
public class DiscountRepository {

private final List<Discount> discounts = new ArrayList<>();

public void addDiscounts(List<Discount> discounts) {
this.discounts.addAll(discounts);
}

public List<Discount> getAllDiscounts() {
return new ArrayList<>(discounts); // Return a copy
}

public List<Discount> findDiscountsByProductIdAndStoreName(String productId, String storeName) {
return discounts.stream()
.filter(discount -> discount.getProductId().equals(productId) && (storeName == null || discount.getStoreName().equals(storeName)))
.collect(Collectors.toList());
}
}