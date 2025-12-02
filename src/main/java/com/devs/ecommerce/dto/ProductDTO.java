package com.devs.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    @NotBlank(message = "Product name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    @Positive(message = "Price must be positive")
    private BigDecimal price;
    @PositiveOrZero(message = "Quantity must be positive")
    private Integer quantity;
    private List<String> images = new ArrayList<>();
    @NotNull(message = "Product category is required")
    private CategoryResponseDTO category;
    @NotNull(message = "Product brand is required")
    private BrandResponseDTO brand;
}
