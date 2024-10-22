package com.example.ShoppingCart.service.product;

import com.example.ShoppingCart.dto.ProductDto;
import com.example.ShoppingCart.model.Product;
import com.example.ShoppingCart.request.AddProductRequest;
import com.example.ShoppingCart.request.ProductUpdateRequest;

import java.util.List;

public interface ProductService {

    Product addProduct(AddProductRequest product);

    List<ProductDto> getConvertedProducts(List<Product> products);

    List<Product> getAllProducts();
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(Long productId, ProductUpdateRequest request);
    List<Product> getProductByCategory(String category);
    List<Product> getProductByBrand(String brand);
    List<Product> getProductByCategoryAndBrand(String category, String brand);
    List<Product> getProductByName(String name);
    List<Product> getProductByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);

    ProductDto convertToDto(Product product);
}
