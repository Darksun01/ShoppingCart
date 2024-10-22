package com.example.ShoppingCart.dto;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private Long id;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private ProductDto product;
}
