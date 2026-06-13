package com.abcfoodzone.abc_food_zone.service;

import com.abcfoodzone.abc_food_zone.entity.Order;
import com.abcfoodzone.abc_food_zone.entity.OrderItem;
import java.util.List;

public interface OrderService {

    // Create order from cart items
    Order createOrder(Long userId, List<OrderItem> items, Double totalAmount);

    // Get order by ID
    Order getOrderById(Long orderId);

    // Get all orders for a user
    List<Order> getOrdersByUser(Long userId);

    // Update order status
    Order updateOrderStatus(Long orderId, String status);

    // Cancel order
    Order cancelOrder(Long orderId);

    // Get all orders (Admin)
    List<Order> getAllOrders();

    // Get pending orders
    List<Order> getPendingOrders();
}