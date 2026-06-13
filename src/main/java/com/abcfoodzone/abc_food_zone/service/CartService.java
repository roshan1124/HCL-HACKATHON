package com.abcfoodzone.abc_food_zone.service;

import com.abcfoodzone.abc_food_zone.entity.Cart;
import com.abcfoodzone.abc_food_zone.entity.CartItem;
import java.util.List;

public interface CartService {

    // Cart methods
    Cart createCart(Long userId);

    Cart getCartByUserId(Long userId);

    Cart getOrCreateCart(Long userId);

    void deleteCart(Long userId);

    boolean existsByUserId(Long userId);

    // CartItem methods (ADD THESE)
    Cart addItemToCart(Long userId, Long productId, String productName, Integer quantity, Double price);

    Cart updateCartItemQuantity(Long userId, Long itemId, Integer quantity);

    Cart removeItemFromCart(Long userId, Long itemId);

    void clearCart(Long userId);

    List<CartItem> getCartItems(Long userId);
}