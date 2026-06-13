package com.abcfoodzone.abc_food_zone.service.impl;

import com.abcfoodzone.abc_food_zone.entity.Order;
import com.abcfoodzone.abc_food_zone.entity.OrderItem;
import com.abcfoodzone.abc_food_zone.repository.OrderItemRepository;
import com.abcfoodzone.abc_food_zone.repository.OrderRepository;
import com.abcfoodzone.abc_food_zone.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public Order createOrder(Long userId, List<OrderItem> items, Double totalAmount) {
        // Create order
        Order order = new Order(userId, totalAmount);
        Order savedOrder = orderRepository.save(order);

        // Add items to order
        for (OrderItem item : items) {
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
            savedOrder.addItem(item);
        }

        return savedOrder;
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }

    @Override
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public Order updateOrderStatus(Long orderId, String status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order cancelOrder(Long orderId) {
        Order order = getOrderById(orderId);
        order.setStatus("CANCELLED");
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getPendingOrders() {
        return orderRepository.findByStatus("PENDING");
    }
}