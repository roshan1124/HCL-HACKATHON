package com.abcfoodzone.abc_food_zone.repository;

import com.abcfoodzone.abc_food_zone.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByIsAvailableTrue();

    List<Product> findByNameContainingIgnoreCase(String name);
}