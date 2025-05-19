package com.comparator.price_comparator.model;

import java.util.List;

import lombok.Data;

@Data
public class ShoppingBasket {
    private String userId;
    private List<BasketItem> items;
}
