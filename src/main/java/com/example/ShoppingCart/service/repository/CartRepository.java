package com.example.ShoppingCart.service.repository;

import com.example.ShoppingCart.model.Cart;
import com.example.ShoppingCart.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
