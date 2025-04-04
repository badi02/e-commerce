package com.devs.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryResponseDTO {
    private Long id;
    @NotBlank(message = "Category name is required")
    private String name;
    @NotBlank(message = "Category name is required")
    private String description;
}
