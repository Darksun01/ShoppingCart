package com.example.ShoppingCart.repository;

import com.example.ShoppingCart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository <CartItem, Long>{
    void deleteAllByCartId(Long id);
}
