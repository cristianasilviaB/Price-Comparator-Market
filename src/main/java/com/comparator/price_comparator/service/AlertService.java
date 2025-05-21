package com.comparator.price_comparator.service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.comparator.price_comparator.model.PriceAlert;
import com.comparator.price_comparator.model.Product;
import com.comparator.price_comparator.repository.PriceAlertRepository;

import java.util.List;

 @Service
 public class AlertService {

  private final PriceAlertRepository priceAlertRepository;
  private final PricingService pricingService;
  private final JavaMailSender mailSender;

  public AlertService(PriceAlertRepository priceAlertRepository, PricingService pricingService,
  JavaMailSender mailSender) {
  this.priceAlertRepository = priceAlertRepository;
  this.pricingService = pricingService;
  this.mailSender = mailSender;
  }

  public PriceAlert createPriceAlert(PriceAlert alert) {
  alert.setEmailSent(false);
  return priceAlertRepository.save(alert);
  }

  @Scheduled(fixedRate = 60000) // Run every hour (3600000 milliseconds)
  public void checkPriceAlerts() {
  List<PriceAlert> alerts = priceAlertRepository.findByEmailSentIsFalse();

  for (PriceAlert alert : alerts) {
  Product product = pricingService.getProductById(alert.getProductId()); // Assuming you add this method
  if (product != null && product.getPrice() <= alert.getTargetPrice()) {
  sendPriceAlertEmail(alert);
  alert.setEmailSent(true);
  priceAlertRepository.save(alert);
  }
  }
  }

  private void sendPriceAlertEmail(PriceAlert alert) {
  // Placeholder for sending email
  SimpleMailMessage message = new SimpleMailMessage();
  message.setTo("cristianasilvia0@gmail.com"); // Replace with user's email (hardcoded for simplicity)
  message.setSubject("Price Alert for Product ID: " + alert.getProductId());
  message.setText("The target price of " + alert.getTargetPrice() + " has been reached!");
  mailSender.send(message);
  }
 }