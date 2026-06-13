package com.abcfoodzone.abc_food_zone.controller;

import com.abcfoodzone.abc_food_zone.entity.Inventory;
import com.abcfoodzone.abc_food_zone.entity.Product;
import com.abcfoodzone.abc_food_zone.repository.ProductRepository;
import com.abcfoodzone.abc_food_zone.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "*")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ProductRepository productRepository;

    // Get all inventory (Admin only)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Inventory>> getAllInventory() {
        List<Inventory> inventory = inventoryService.getAllInventory();
        return ResponseEntity.ok(inventory);
    }

    // Get inventory by product ID
    @GetMapping("/product/{productId}")
    public ResponseEntity<Inventory> getInventoryByProductId(@PathVariable Long productId) {
        Inventory inventory = inventoryService.getInventoryByProductId(productId);
        return ResponseEntity.ok(inventory);
    }

    // Check stock availability
    @GetMapping("/check/{productId}")
    public ResponseEntity<Boolean> checkAvailability(@PathVariable Long productId, @RequestParam Integer quantity) {
        boolean available = inventoryService.checkAvailability(productId, quantity);
        return ResponseEntity.ok(available);
    }

    // Get available stock
    @GetMapping("/stock/{productId}")
    public ResponseEntity<Integer> getAvailableStock(@PathVariable Long productId) {
        Integer stock = inventoryService.getAvailableStock(productId);
        return ResponseEntity.ok(stock);
    }

    // Get low stock items (Admin only)
    @GetMapping("/low-stock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Inventory>> getLowStockItems() {
        List<Inventory> lowStock = inventoryService.getLowStockItems();
        return ResponseEntity.ok(lowStock);
    }

    // Create inventory for product (Admin only)
    @PostMapping("/product/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Inventory> createInventory(@PathVariable Long productId, @RequestParam Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Inventory inventory = inventoryService.createInventory(product, quantity);
        return new ResponseEntity<>(inventory, HttpStatus.CREATED);
    }

    // Create inventory with threshold (Admin only)
    @PostMapping("/product/{productId}/with-threshold")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Inventory> createInventoryWithThreshold(@PathVariable Long productId,
                                                                  @RequestParam Integer quantity,
                                                                  @RequestParam Integer threshold) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Inventory inventory = inventoryService.createInventory(product, quantity, threshold);
        return new ResponseEntity<>(inventory, HttpStatus.CREATED);
    }

    // Update stock (Admin only)
    @PutMapping("/product/{productId}/stock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Inventory> updateStock(@PathVariable Long productId, @RequestParam Integer newQuantity) {
        Inventory inventory = inventoryService.updateStock(productId, newQuantity);
        return ResponseEntity.ok(inventory);
    }

    // Add stock (Admin only)
    @PutMapping("/product/{productId}/add-stock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Inventory> addStock(@PathVariable Long productId, @RequestParam Integer quantity) {
        Inventory inventory = inventoryService.addStock(productId, quantity);
        return ResponseEntity.ok(inventory);
    }

    // Reduce stock (Admin only)
    @PutMapping("/product/{productId}/reduce-stock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Inventory> reduceStock(@PathVariable Long productId, @RequestParam Integer quantity) {
        Inventory inventory = inventoryService.reduceStock(productId, quantity);
        return ResponseEntity.ok(inventory);
    }

    // Update threshold (Admin only)
    @PutMapping("/product/{productId}/threshold")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Inventory> updateThreshold(@PathVariable Long productId, @RequestParam Integer threshold) {
        Inventory inventory = inventoryService.updateThreshold(productId, threshold);
        return ResponseEntity.ok(inventory);
    }

    // Initialize inventory for product (Admin only)
    @PostMapping("/initialize/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Inventory> initializeInventory(@PathVariable Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Inventory inventory = inventoryService.initializeInventory(product);
        return ResponseEntity.ok(inventory);
    }
}