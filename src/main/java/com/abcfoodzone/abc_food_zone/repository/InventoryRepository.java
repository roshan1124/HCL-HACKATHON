package com.abcfoodzone.abc_food_zone.repository;

import com.abcfoodzone.abc_food_zone.entity.Inventory;
import com.abcfoodzone.abc_food_zone.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProduct(Product product);

    Optional<Inventory> findByProductId(Long productId);

    List<Inventory> findByAvailableQuantityLessThanEqual(Integer threshold);

    List<Inventory> findByAvailableQuantityEquals(Integer quantity);

    boolean existsByProductId(Long productId);
}