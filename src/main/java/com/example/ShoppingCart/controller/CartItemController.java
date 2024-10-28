package com.example.ShoppingCart.controller;

import com.example.ShoppingCart.Response.ApiResponse;
import com.example.ShoppingCart.model.Cart;
import com.example.ShoppingCart.model.User;
import com.example.ShoppingCart.service.User.UserService;
import com.example.ShoppingCart.service.cart.CartItemService;
import com.example.ShoppingCart.service.cart.CartService;
import com.example.ShoppingCart.exceptions.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {

    private final CartItemService cartItemService;
    private final CartService cartService;
    private final UserService userService;

    @PostMapping("/addItems")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long productId,
                                                     @RequestParam Integer quantity){
        try {
            User user = userService.getAuthenticatedUser();
            Cart cart = cartService.initializeNewCart(user);

            cartItemService.addCartItem(cart.getId(), productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Items Added Successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }catch (JwtException e){
            return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));

        }
    }

    @DeleteMapping("/{cartId}/cart/{productId}/product/delete")
    public ResponseEntity<ApiResponse> removeItemsFromCart(@PathVariable Long cartId, @PathVariable Long productId){
        try {
            cartItemService.removeItemFromCart(cartId, productId);
            return ResponseEntity.ok(new ApiResponse("Removed Item successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));        }
    }

    @PutMapping("/{cartId}/cart/{productId}/product/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
                                                          @PathVariable Long productId,
                                                          @RequestParam Integer quantity){
        try {
            cartItemService.updateItemQuantity(cartId, productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Quantity updated Successfully", null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
