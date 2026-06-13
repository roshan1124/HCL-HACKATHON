package com.abcfoodzone.abc_food_zone.controller;

import com.abcfoodzone.abc_food_zone.entity.Cart;
import com.abcfoodzone.abc_food_zone.entity.CartItem;
import com.abcfoodzone.abc_food_zone.entity.User;
import com.abcfoodzone.abc_food_zone.repository.UserRepository;
import com.abcfoodzone.abc_food_zone.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    // Get current user's cart
    @GetMapping
    public ResponseEntity<Cart> getCart() {
        Long userId = getCurrentUserId();
        Cart cart = cartService.getOrCreateCart(userId);
        return ResponseEntity.ok(cart);
    }

    // Get all cart items
    @GetMapping("/items")
    public ResponseEntity<List<CartItem>> getCartItems() {
        Long userId = getCurrentUserId();
        List<CartItem> items = cartService.getCartItems(userId);
        return ResponseEntity.ok(items);
    }

    // Add item to cart
    @PostMapping("/add")
    public ResponseEntity<Cart> addItemToCart(
            @RequestParam Long productId,
            @RequestParam String productName,
            @RequestParam Integer quantity,
            @RequestParam Double price) {
        Long userId = getCurrentUserId();
        Cart cart = cartService.addItemToCart(userId, productId, productName, quantity, price);
        return ResponseEntity.ok(cart);
    }

    // Update cart item quantity
    @PutMapping("/update/{itemId}")
    public ResponseEntity<Cart> updateCartItem(
            @PathVariable Long itemId,
            @RequestParam Integer quantity) {
        Long userId = getCurrentUserId();
        Cart cart = cartService.updateCartItemQuantity(userId, itemId, quantity);
        return ResponseEntity.ok(cart);
    }

    // Remove item from cart
    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<String> removeCartItem(@PathVariable Long itemId) {
        Long userId = getCurrentUserId();
        cartService.removeItemFromCart(userId, itemId);
        return ResponseEntity.ok("Item removed from cart successfully!");
    }

    // Clear all items from cart
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart() {
        Long userId = getCurrentUserId();
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared successfully!");
    }

    // Create cart for current user
    @PostMapping
    public ResponseEntity<Cart> createCart() {
        Long userId = getCurrentUserId();

        if (cartService.existsByUserId(userId)) {
            return ResponseEntity.badRequest().build();
        }

        Cart cart = cartService.createCart(userId);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    // Delete cart
    @DeleteMapping
    public ResponseEntity<String> deleteCart() {
        Long userId = getCurrentUserId();
        cartService.deleteCart(userId);
        return ResponseEntity.ok("Cart deleted successfully!");
    }

    // Helper method to get current logged-in user ID
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getId();
    }
}