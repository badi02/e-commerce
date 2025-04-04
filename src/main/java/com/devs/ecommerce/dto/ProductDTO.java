package com.devs.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    @NotBlank(message = "Product name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    @Positive(message = "Price must be positive")
    private double price;
    @PositiveOrZero(message = "Quantity must be positive")
    private Integer quantity;
    private String image;
    @NotNull(message = "Product category is required")
    private CategoryResponseDTO category;
    @NotNull(message = "Product brand is required")
    private BrandResponseDTO brand;
    //private List<CommentDTO> comments;
}
