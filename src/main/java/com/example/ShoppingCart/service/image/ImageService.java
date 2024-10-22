package com.example.ShoppingCart.service.image;

import com.example.ShoppingCart.dto.ImageDto;
import com.example.ShoppingCart.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile> file, Long productId);
    void updateImage(Long id, MultipartFile file);
}
