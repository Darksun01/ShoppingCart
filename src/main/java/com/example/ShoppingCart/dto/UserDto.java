package com.example.ShoppingCart.dto;


import com.example.ShoppingCart.model.Cart;
import com.example.ShoppingCart.model.Order;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDto> orders;
    private CartDto cart;

}
