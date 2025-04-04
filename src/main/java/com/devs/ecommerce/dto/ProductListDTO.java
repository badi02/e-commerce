package com.devs.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductListDTO {
    private Long id;
    @NotBlank(message = "Product name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    @Positive(message = "Price ust be positive")
    private double price;
    @PositiveOrZero(message = "Quantity must be positive")
    private Integer quantity;
    private String image;
    @NotBlank(message = "Product category is required")
    private String categoryName;
    @NotBlank(message = "Product brand is required")
    private String brandName;
}
