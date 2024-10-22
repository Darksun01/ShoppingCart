package com.example.ShoppingCart.repository;

import com.example.ShoppingCart.model.Image;
import com.example.ShoppingCart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProductId(Long id);
}
