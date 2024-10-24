package com.example.ShoppingCart.service.cart;

import com.example.ShoppingCart.model.Cart;
import com.example.ShoppingCart.model.User;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
