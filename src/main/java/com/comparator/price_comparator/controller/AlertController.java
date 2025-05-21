package com.comparator.price_comparator.controller;

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
 }