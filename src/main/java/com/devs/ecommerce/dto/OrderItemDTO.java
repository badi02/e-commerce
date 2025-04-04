package com.devs.ecommerce.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;


@Data
public class OrderItemDTO {
    private Long id;
    private Long productId;
    @PositiveOrZero()
    private Integer quantity;
    @Positive()
    private double price;
}
