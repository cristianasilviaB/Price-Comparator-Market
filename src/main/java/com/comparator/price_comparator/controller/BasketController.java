 package com.comparator.price_comparator.controller;

 import java.util.List;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RestController;

 import com.comparator.price_comparator.model.BasketItem;
 import com.comparator.price_comparator.model.ShoppingBasket;
 import com.comparator.price_comparator.model.ShoppingList;
 import com.comparator.price_comparator.service.BasketService;

 @RestController
 @RequestMapping("/basket")
 public class BasketController {

  @Autowired
  private BasketService basketService;

  @PostMapping("/{userId}/add")
  public void addToBasket(@PathVariable String userId, @RequestBody BasketItem item) {
  basketService.addToBasket(userId, item.getProductId(), item.getStoreName() ,item.getQuantity());
  }

  @GetMapping("/{userId}")
  public ShoppingBasket viewBasket(@PathVariable String userId) {
  return basketService.getBasket(userId);
  }

  @GetMapping("/{userId}/optimize")
  public List<ShoppingList> optimizeBasket(@PathVariable String userId) {
  return basketService.optimizeBasket(userId);
  }
 }