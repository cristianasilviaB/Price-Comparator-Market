package com.comparator.price_comparator.repository;

 import java.util.ArrayList;
 import java.util.List;
 import java.util.UUID; // For generating unique IDs

import org.springframework.stereotype.Repository;

import com.comparator.price_comparator.model.PriceAlert;

 @Repository
 public class PriceAlertRepository {

  private final List<PriceAlert> alerts = new ArrayList<>();

  public PriceAlert save(PriceAlert alert) {
  if (alert.getAlertId() == null) {
  alert.setAlertId(UUID.randomUUID().toString()); // Generate unique ID
  } else {
  alerts.removeIf(a -> a.getAlertId().equals(alert.getAlertId())); // Update
  }
  alerts.add(alert);
  return alert;
  }

  public List<PriceAlert> findByProductId(String productId) {
  return alerts.stream()
  .filter(alert -> alert.getProductId().equals(productId))
  .toList();
  }

  public List<PriceAlert> findByEmailSentIsFalse() {
  return alerts.stream()
  .filter(alert -> !alert.isEmailSent())
  .toList();
  }
 }
