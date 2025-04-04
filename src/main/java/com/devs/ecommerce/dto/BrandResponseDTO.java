package com.devs.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BrandResponseDTO {
    private Long id;
    @NotBlank(message = "Brand name is required")
    private String name;
}
