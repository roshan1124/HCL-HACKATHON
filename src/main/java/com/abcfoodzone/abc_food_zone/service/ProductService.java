package com.abcfoodzone.abc_food_zone.service;

import com.abcfoodzone.abc_food_zone.entity.Product;
import java.util.List;

public interface ProductService {

    Product createProduct(Product product);

    Product updateProduct(Long id, Product product);

    void deleteProduct(Long id);

    Product getProductById(Long id);

    List<Product> getAllProducts();

    List<Product> getAvailableProducts();

    List<Product> searchProductsByName(String name);
}