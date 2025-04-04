package com.devs.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    @NotBlank(message = "Customer name is required")
    private String customerName;
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    @NotBlank(message = "Town is required")
    private String town;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "Delivery Tax is required")
    private List<OrderItemRequestDTO> items;
}
