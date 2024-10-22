package com.example.ShoppingCart.service.Order;

import com.example.ShoppingCart.dto.OrderDto;
import com.example.ShoppingCart.model.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);
}
