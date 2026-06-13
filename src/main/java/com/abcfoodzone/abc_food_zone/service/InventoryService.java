package com.abcfoodzone.abc_food_zone.service;

import com.abcfoodzone.abc_food_zone.entity.Inventory;
import com.abcfoodzone.abc_food_zone.entity.Product;
import java.util.List;

public interface InventoryService {

    // Create inventory for product
    Inventory createInventory(Product product, Integer availableQuantity);

    Inventory createInventory(Product product, Integer availableQuantity, Integer minimumThreshold);

    // Get inventory
    Inventory getInventoryByProductId(Long productId);

    Inventory getInventoryByProduct(Product product);

    List<Inventory> getAllInventory();

    // Update stock
    Inventory updateStock(Long productId, Integer newQuantity);

    Inventory addStock(Long productId, Integer quantityToAdd);

    Inventory reduceStock(Long productId, Integer quantityToReduce);

    // Check stock
    boolean checkAvailability(Long productId, Integer requestedQuantity);

    Integer getAvailableStock(Long productId);

    // Low stock
    List<Inventory> getLowStockItems();

    // Update threshold
    Inventory updateThreshold(Long productId, Integer newThreshold);

    // Initialize inventory for new product
    Inventory initializeInventory(Product product);
}