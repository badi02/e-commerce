package com.devs.ecommerce.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class OrderItemRequestDTO {
    private Long productId;
    @PositiveOrZero()
    private Integer quantity;
}
