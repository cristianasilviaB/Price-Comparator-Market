package com.comparator.price_comparator.service;

 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;

 import com.comparator.price_comparator.model.BasketItem;
 import com.comparator.price_comparator.model.Product;
 import com.comparator.price_comparator.model.ShoppingBasket;
 import com.comparator.price_comparator.model.ShoppingList;
 import com.comparator.price_comparator.repository.ProductRepository;

 import lombok.extern.slf4j.Slf4j;

 @Service
 @Slf4j
 public class BasketService {

  @Autowired
  private ProductRepository productRepository;

  private final Map<String, ShoppingBasket> baskets = new HashMap<>(); // In-memory basket storage

  public ShoppingBasket getBasket(String userId) {
  return baskets.computeIfAbsent(userId, k -> {
  ShoppingBasket newBasket = new ShoppingBasket();
  newBasket.setUserId(userId);
  newBasket.setItems(new ArrayList<>());
  return newBasket;
  });
 }

  public void addToBasket(String userId, String productId, String storeName, int quantity) {
  ShoppingBasket basket = getBasket(userId);
  List<BasketItem> items = basket.getItems();

  // Check if the item already exists in the basket (considering both productId and storeName)
  boolean itemExists = false;
  for (BasketItem item : items) {
  if (item.getProductId().equals(productId) && item.getStoreName().equals(storeName)) {
  item.setQuantity(item.getQuantity() + quantity);
  itemExists = true;
  break;
  }
  }

  // If the item doesn't exist, add it
  if (!itemExists) {
  BasketItem newItem = new BasketItem();
  newItem.setProductId(productId);
  newItem.setStoreName(storeName);
  newItem.setQuantity(quantity);
  items.add(newItem);
  }

  log.info("Added {} of product {} from store {} to basket for user {}", quantity, productId, storeName, userId);
 }

  public List<ShoppingList> optimizeBasket(String userId) {
  ShoppingBasket basket = getBasket(userId);
  List<BasketItem> basketItems = basket.getItems();

  // Create a map to store shopping lists by store
  Map<String, ShoppingList> storeLists = new HashMap<>();

  // Iterate through each item in the basket
  for (BasketItem basketItem : basketItems) {
  String productId = basketItem.getProductId();
  String storeName = basketItem.getStoreName();
  int quantity = basketItem.getQuantity();

  // Get product details from the repository
  Product product = productRepository.getProductById(productId, storeName); // Assuming you update ProductRepository
  if (product == null) {
  log.warn("Product with ID {} and store {} not found", productId, storeName);
  continue; // Skip to the next item if product not found
  }

  double productPrice = product.getPrice();

  // Get or create the shopping list for the store
  ShoppingList shoppingList = storeLists.computeIfAbsent(storeName, k -> {
  ShoppingList newList = new ShoppingList();
  newList.setStoreName(storeName);
  newList.setItems(new ArrayList<>());
  newList.setTotalCost(0); // Initialize total cost
  return newList;
  });

  // Add the item to the shopping list
  shoppingList.getItems().add(basketItem);

  // Update the total cost of the shopping list
  double itemCost = productPrice * quantity;
  shoppingList.setTotalCost(shoppingList.getTotalCost() + itemCost);
  }

  // Convert the map of shopping lists to a list
  return new ArrayList<>(storeLists.values());
 }
 }