package com.devs.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class TownDTO {
    private Long id;
    @NotBlank(message = "Town name is required")
    private String name;
    @PositiveOrZero(message = "Delivery task must be positive or zero")
    private double deliveryTax;
}
