package com.example.ShoppingCart.controller;

import com.example.ShoppingCart.Response.ApiResponse;
import com.example.ShoppingCart.dto.OrderDto;
import com.example.ShoppingCart.exceptions.ResourceNotFoundException;
import com.example.ShoppingCart.model.Order;
import com.example.ShoppingCart.service.Order.OrderService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId){
        try {
            Order order = orderService.placeOrder(userId);
            OrderDto orderDto = orderService.convertToDto(order);
            return ResponseEntity.ok(new ApiResponse("Order created!", orderDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId){
        try {
            OrderDto order = orderService.getOrder(orderId);
            return  ResponseEntity.ok(new ApiResponse("Here's your order", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No order found", e.getMessage()));
        }
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId){
        try {
            List<OrderDto> orders = orderService.getUserOrders(userId);
            return  ResponseEntity.ok(new ApiResponse("Here's your orders", orders));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }


}
