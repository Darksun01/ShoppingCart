package com.example.ShoppingCart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int inventory;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)// if a product is deleted all the images associated with the product will be deleted
    private List<Image> images;


    public Product(String name, String brand, int inventory, String description, BigDecimal price, Category category) {
        this.name = name;
        this.brand = brand;
        this.inventory = inventory;
        this.description = description;
        this.price = price;
        this.category = category;
    }
}
