package com.abcfoodzone.abc_food_zone.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "available_quantity", nullable = false)
    private Integer availableQuantity = 0;

    @Column(name = "minimum_threshold", nullable = false)
    private Integer minimumThreshold = 5;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    // Constructors
    public Inventory() {}

    public Inventory(Product product, Integer availableQuantity) {
        this.product = product;
        this.availableQuantity = availableQuantity;
        this.minimumThreshold = 5;
        this.lastUpdated = LocalDateTime.now();
    }

    public Inventory(Product product, Integer availableQuantity, Integer minimumThreshold) {
        this.product = product;
        this.availableQuantity = availableQuantity;
        this.minimumThreshold = minimumThreshold;
        this.lastUpdated = LocalDateTime.now();
    }

    // Helper methods
    public boolean isInStock() {
        return availableQuantity > 0;
    }

    public boolean isLowStock() {
        return availableQuantity <= minimumThreshold;
    }

    public boolean hasStock(int requestedQuantity) {
        return availableQuantity >= requestedQuantity;
    }

    public void reduceStock(int quantity) {
        if (hasStock(quantity)) {
            this.availableQuantity -= quantity;
            this.lastUpdated = LocalDateTime.now();
        } else {
            throw new RuntimeException("Insufficient stock. Available: " + availableQuantity);
        }
    }

    public void increaseStock(int quantity) {
        this.availableQuantity += quantity;
        this.lastUpdated = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
        this.lastUpdated = LocalDateTime.now();
    }

    public Integer getMinimumThreshold() {
        return minimumThreshold;
    }

    public void setMinimumThreshold(Integer minimumThreshold) {
        this.minimumThreshold = minimumThreshold;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}