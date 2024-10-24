package com.example.ShoppingCart.service.User;

import com.example.ShoppingCart.dto.UserDto;
import com.example.ShoppingCart.exceptions.AlreadyExistsException;
import com.example.ShoppingCart.exceptions.ResourceNotFoundException;
import com.example.ShoppingCart.model.User;
import com.example.ShoppingCart.repository.UserRepository;
import com.example.ShoppingCart.request.CreateUserRequest;
import com.example.ShoppingCart.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User nor found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request).
                filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    return userRepository.save(user);
                }).orElseThrow(() -> new AlreadyExistsException(request.getEmail() + " already exists!"));
    }

    @Override
    public User updateUser(Long userId, UserUpdateRequest request) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository :: delete, ()->{
                    new ResourceNotFoundException("User not found");
                });
    }

    @Override
    public UserDto convertUserToDto(User user){
        return modelMapper.map(user, UserDto.class);

    }
}
