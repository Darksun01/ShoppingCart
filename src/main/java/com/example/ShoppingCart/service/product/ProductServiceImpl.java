package com.example.ShoppingCart.service.product;

import com.example.ShoppingCart.dto.ImageDto;
import com.example.ShoppingCart.dto.ProductDto;
import com.example.ShoppingCart.model.Category;
import com.example.ShoppingCart.model.Image;
import com.example.ShoppingCart.model.Product;
import com.example.ShoppingCart.request.AddProductRequest;
import com.example.ShoppingCart.request.ProductUpdateRequest;
import com.example.ShoppingCart.exceptions.ResourceNotFoundException;
import com.example.ShoppingCart.repository.CategoryRepository;
import com.example.ShoppingCart.repository.ImageRepository;
import com.example.ShoppingCart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    private final ImageRepository imageRepository;

    @Override
    public Product addProduct(AddProductRequest request) {
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName())).
                orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
       return  productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest addProductRequest, Category category){
        return new Product(
                addProductRequest.getName(),
                addProductRequest.getBrand(),
                addProductRequest.getInventory(),
                addProductRequest.getDescription(),
                addProductRequest.getPrice(),
                category
        );
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products){
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<Product> getAllProducts() {
        return  productRepository.findAll();

    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> product =  productRepository.findById(id);
        if(product.isEmpty()){
            throw new ResourceNotFoundException("Product not found");
        }
        return  product.get();
    }

    @Override
    public void deleteProductById(Long id) {
       productRepository.findById(id)
               .ifPresentOrElse(productRepository::delete, () -> {
                   throw new ResourceNotFoundException("No Product to delete");
               });
    }

    @Override
    public List<Product> getProductByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public Product updateProduct(Long productId, ProductUpdateRequest request) {
        return productRepository.findById(productId)
                .map(existingPorduct -> updateExistingProduct(existingPorduct, request))
                .map(productRepository :: save)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category  = categoryRepository.findByName(existingProduct.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public ProductDto convertToDto(Product product){
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream().map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        productDto.setImages(imageDtos);
        return  productDto;
    }
}
