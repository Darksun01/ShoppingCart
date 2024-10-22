package com.example.ShoppingCart.service.User;

import com.example.ShoppingCart.model.User;
import com.example.ShoppingCart.request.CreateUserRequest;
import com.example.ShoppingCart.request.UserUpdateRequest;

public interface UserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(Long userId, UserUpdateRequest request);
    void deleteUser(Long userId);
}
