package com.example.ShoppingCart.controller;

import com.example.ShoppingCart.Response.ApiResponse;
import com.example.ShoppingCart.dto.ProductDto;
import com.example.ShoppingCart.model.Product;
import com.example.ShoppingCart.request.AddProductRequest;
import com.example.ShoppingCart.request.ProductUpdateRequest;
import com.example.ShoppingCart.service.exceptions.ResourceNotFoundException;
import com.example.ShoppingCart.service.product.ProductService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        List<Product> productList = productService.getAllProducts();
        List<ProductDto> productDtos = productService.getConvertedProducts(productList);
        return ResponseEntity.ok(new ApiResponse("Found", productDtos));
    }


    @GetMapping("/{id}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id){
        try {
            Product product = productService.getProductById(id);
            var productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Success", productDto));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name){
        try {
            List<Product> productList = productService.getProductByName(name);
            List<ProductDto> productDtos = productService.getConvertedProducts(productList);
            if(productList.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Products Found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/brand/{brandName}/product/{productName}")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@PathVariable String brandName, @PathVariable String productName){
        try {
            List<Product> productList = productService.getProductByBrandAndName(brandName, productName);
            List<ProductDto> productDtos = productService.getConvertedProducts(productList);
            if(productList.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Products Found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/brand/{brandName}/product/{productName}/count")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@PathVariable String brandName, @PathVariable String productName){
        try {
            Long productCount = productService.countProductsByBrandAndName(brandName, productName);
            return ResponseEntity.ok(new ApiResponse("Product Count", productCount));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{categoryName}/brand/{brandName}")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@PathVariable String categoryName, @PathVariable String brandName){
        try {
            List<Product> productList = productService.getProductByCategoryAndBrand(categoryName, brandName);
            List<ProductDto> productDtos = productService.getConvertedProducts(productList);
            if(productList.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Products Found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/brand/{brandName}")
    public ResponseEntity<ApiResponse> getProductByBrand(@PathVariable String brandName){
        try {
            List<Product> productList = productService.getProductByBrand(brandName);
            List<ProductDto> productDtos = productService.getConvertedProducts(productList);
            if(productList.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Products Found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String categoryName){
        try {
            List<Product> productList = productService.getProductByCategory(categoryName);
            List<ProductDto> productDtos = productService.getConvertedProducts(productList);
            if(productList.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Products Found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest request){
        try {
            Product newProduct = productService.addProduct(request);
            return ResponseEntity.ok(new ApiResponse("Add Success", newProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id,@RequestBody ProductUpdateRequest request){
        try {
            Product newProduct = productService.updateProduct(id, request);
            return ResponseEntity.ok(new ApiResponse("Update Success", newProduct));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id){
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok(new ApiResponse("Delete Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
