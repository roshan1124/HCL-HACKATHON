package com.abcfoodzone.abc_food_zone.service.impl;

import com.abcfoodzone.abc_food_zone.entity.Inventory;
import com.abcfoodzone.abc_food_zone.entity.Product;
import com.abcfoodzone.abc_food_zone.repository.InventoryRepository;
import com.abcfoodzone.abc_food_zone.repository.ProductRepository;
import com.abcfoodzone.abc_food_zone.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Inventory createInventory(Product product, Integer availableQuantity) {
        Inventory inventory = new Inventory(product, availableQuantity);
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory createInventory(Product product, Integer availableQuantity, Integer minimumThreshold) {
        Inventory inventory = new Inventory(product, availableQuantity, minimumThreshold);
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory getInventoryByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for product: " + productId));
    }

    @Override
    public Inventory getInventoryByProduct(Product product) {
        return inventoryRepository.findByProduct(product)
                .orElseThrow(() -> new RuntimeException("Inventory not found for product: " + product.getName()));
    }

    @Override
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    @Override
    @Transactional
    public Inventory updateStock(Long productId, Integer newQuantity) {
        Inventory inventory = getInventoryByProductId(productId);
        inventory.setAvailableQuantity(newQuantity);
        return inventoryRepository.save(inventory);
    }

    @Override
    @Transactional
    public Inventory addStock(Long productId, Integer quantityToAdd) {
        Inventory inventory = getInventoryByProductId(productId);
        inventory.increaseStock(quantityToAdd);
        return inventoryRepository.save(inventory);
    }

    @Override
    @Transactional
    public Inventory reduceStock(Long productId, Integer quantityToReduce) {
        Inventory inventory = getInventoryByProductId(productId);
        inventory.reduceStock(quantityToReduce);
        return inventoryRepository.save(inventory);
    }

    @Override
    public boolean checkAvailability(Long productId, Integer requestedQuantity) {
        try {
            Inventory inventory = getInventoryByProductId(productId);
            return inventory.hasStock(requestedQuantity);
        } catch (RuntimeException e) {
            return false;
        }
    }

    @Override
    public Integer getAvailableStock(Long productId) {
        try {
            Inventory inventory = getInventoryByProductId(productId);
            return inventory.getAvailableQuantity();
        } catch (RuntimeException e) {
            return 0;
        }
    }

    @Override
    public List<Inventory> getLowStockItems() {
        return inventoryRepository.findByAvailableQuantityLessThanEqual(5);
    }

    @Override
    @Transactional
    public Inventory updateThreshold(Long productId, Integer newThreshold) {
        Inventory inventory = getInventoryByProductId(productId);
        inventory.setMinimumThreshold(newThreshold);
        return inventoryRepository.save(inventory);
    }

    @Override
    @Transactional
    public Inventory initializeInventory(Product product) {
        if (inventoryRepository.existsByProductId(product.getId())) {
            return getInventoryByProductId(product.getId());
        }
        return createInventory(product, 10);
    }
}