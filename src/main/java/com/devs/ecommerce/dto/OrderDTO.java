package com.devs.ecommerce.dto;

import com.devs.ecommerce.model.Order;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    @NotBlank(message = "Customer name is required")
    private String name;
    @NotBlank(message = "Order address is required")
    private String address;
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Order.OrderStatus status;
    private LocalDateTime createdAt;
    private List<OrderItemDTO> orderItems;
}
