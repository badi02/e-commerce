package com.devs.ecommerce.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class OrderItemResponseDTO {
    private Long productId;
    @NotBlank(message = "Product name is required")
    private String productName;
    @NotBlank(message = "Product description is required")
    private String productDescription;
    @PositiveOrZero(message = "quantity should be positive or zero")
    private Integer quantity;
    @Positive(message = "Price is always positive")
    private BigDecimal price; // Price per unit
}
