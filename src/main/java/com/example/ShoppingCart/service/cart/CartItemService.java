package com.example.ShoppingCart.service.cart;

import com.example.ShoppingCart.model.CartItem;

public interface CartItemService {
    void addCartItem(Long cartId, Long productId, Integer quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, Integer quantity);
}
