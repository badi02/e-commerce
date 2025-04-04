package com.devs.ecommerce.mapper;

import com.devs.ecommerce.dto.*;
import com.devs.ecommerce.model.Order;
import com.devs.ecommerce.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    // Convert Order entity to OrderResponse DTO
    @Mapping(target = "items", source = "items")
    @Mapping(target = "price", source = "totalPrice")
    @Mapping(target = "deliveryTax", source = "deliveryTax")
    @Mapping(target = "customerName", source = "name")
    OrderResponseDTO toResponse(Order order);

    // Convert OrderRequest DTO to Order entity
    @Mapping(target = "items", source = "items")
    @Mapping(target = "id", ignore = true)  // ID is auto-generated
    @Mapping(target = "status", constant = "PENDING")  // Default order status
    @Mapping(target = "name", source = "customerName")
    Order toEntity(OrderRequestDTO orderRequest);

    // Convert List of Orders to List of OrderResponse DTOs
    List<OrderResponseDTO> toResponses(List<Order> orders);

    // Convert List of OrderRequests to List of Orders
    List<Order> toEntities(List<OrderRequestDTO> orderRequests);

    // Convert OrderItem entity to OrderItemResponse DTO
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "productDescription", source = "product.description")// Include product name
    @Mapping(target = "price", source = "price")  // Include unit price
    OrderItemResponseDTO toOrderItemResponse(OrderItem orderItem);

    // Convert OrderItemRequest DTO to OrderItem entity
    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "id", ignore = true)  // ID is auto-generated
    OrderItem toOrderItemEntity(OrderItemRequestDTO orderItemRequest);

    // Convert List of OrderItem entities to List of OrderItemResponse DTOs
    List<OrderItemResponseDTO> toOrderItemResponses(List<OrderItem> orderItems);

    // Convert List of OrderItemRequests to List of OrderItem entities
    List<OrderItem> toOrderItemEntities(List<OrderItemRequestDTO> orderItemRequests);
}
