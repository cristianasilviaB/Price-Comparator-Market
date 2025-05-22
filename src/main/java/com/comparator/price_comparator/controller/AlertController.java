package com.comparator.price_comparator.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.comparator.price_comparator.model.PriceAlert;
import com.comparator.price_comparator.service.AlertService;

 @RestController
 @RequestMapping("/alerts")
 public class AlertController {

  private final AlertService alertService;

  public AlertController(AlertService alertService) {
  this.alertService = alertService;
  }

  @PostMapping
  public PriceAlert createAlert(@RequestBody PriceAlert alert) {
  return alertService.createPriceAlert(alert);
  }

   @GetMapping("/{alertId}")
  public PriceAlert getAlertById(@PathVariable String alertId) {
  return alertService.getAlertById(alertId);
  }
  @GetMapping("/product/{productId}")
  public List<PriceAlert> getAlertsByProductId(@PathVariable String productId) {
  return alertService.getAlertsByProductId(productId);
  }
 }