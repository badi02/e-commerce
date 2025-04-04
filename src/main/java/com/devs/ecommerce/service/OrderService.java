package com.devs.ecommerce.service;

import com.devs.ecommerce.dto.OrderRequestDTO;
import com.devs.ecommerce.dto.OrderResponseDTO;
import com.devs.ecommerce.exception.InsufficientStockException;
import com.devs.ecommerce.exception.ResourceNotFoundException;
import com.devs.ecommerce.mapper.OrderMapper;
import com.devs.ecommerce.model.*;
import com.devs.ecommerce.repositories.OrderRepository;
import com.devs.ecommerce.repositories.ProductRepository;
import com.devs.ecommerce.repositories.TownRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class  OrderService {

    private final OrderRepository orderRepository;
    private final TownRepository townRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;


    @Transactional
    public OrderResponseDTO placeOrder(OrderRequestDTO orderRequest) {
        // Convert request to entity
        Order order = orderMapper.toEntity(orderRequest);

        // Validate and process order items
        double totalPrice = 0.0;
        for (OrderItem item : order.getItems()) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            // Validate stock
            if (product.getQuantity() < item.getQuantity()) {
                throw new InsufficientStockException("Not enough stock for product: " + product.getName());
            }

            // Deduct stock
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productRepository.save(product);

            item.setProduct(product);
            // Calculate total price
            double itemTotalPrice = item.getQuantity() * product.getPrice();
            item.setPrice(itemTotalPrice);
            totalPrice += itemTotalPrice;

            // **Important: Set the parent Order reference**
            item.setOrder(order);
        }

        // Calculate delivery fee
        double deliveryFee = calculateDeliveryFee(orderRequest.getTown());
        totalPrice += deliveryFee;

        // Set order properties
        order.setTotalPrice(totalPrice);
        order.setDeliveryTax(deliveryFee);
        order.setStatus(Order.OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        // Save order
        orderRepository.save(order);

        return orderMapper.toResponse(order);
    }

    /**
     * Cancel an order and restore stock
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (order.getStatus() == Order.OrderStatus.CANCELED) {
            throw new IllegalStateException("Order is already cancelled");
        }

        // Restore stock
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() + item.getQuantity());
            productRepository.save(product);
        }

        // Update order status
        order.setStatus(Order.OrderStatus.CANCELED);
        orderRepository.save(order);
    }

    /**
     * Get order by ID
     */
    public OrderResponseDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return orderMapper.toResponse(order);
    }

    /**
     * Get all orders
     */
    public List<OrderResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orderMapper.toResponses(orders);
    }

    /**
     * Update order status
     */
    @Transactional
    public void updateOrderStatus(Long orderId, Order.OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);
    }

    /**
     * Calculate delivery fee based on town
     */
    private double calculateDeliveryFee(String townName) {
        Town town = townRepository.findByName(townName)
                .orElseThrow(() -> new ResourceNotFoundException("Town not found"));
        return town.getDeliveryTax();
    }
}
