package com.devs.ecommerce.dto;

import com.devs.ecommerce.model.Order;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long id;
    @NotBlank(message = "Customer name is required")
    private String customerName;
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    @NotBlank(message = "Town is required")
    private String town;
    @NotBlank(message = "Address is required")
    private String address;
    @PositiveOrZero(message = "Delivery Tax is always positive or zero")
    private double deliveryTax;
    @Positive(message = "Price is always positive")
    private double price; // Price per unit
    private Order.OrderStatus status;
    private LocalDateTime createdAt;
    private List<OrderItemResponseDTO> items;
}
