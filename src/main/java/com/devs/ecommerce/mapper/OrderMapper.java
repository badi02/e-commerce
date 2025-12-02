package com.devs.ecommerce.mapper;

import com.devs.ecommerce.dto.*;
import com.devs.ecommerce.model.Order;
import com.devs.ecommerce.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "items", source = "items")
    @Mapping(target = "price", source = "totalPrice")
    @Mapping(target = "deliveryTax", source = "deliveryTax")
    @Mapping(target = "customerName", source = "name")
    OrderResponseDTO toResponse(Order order);

    @Mapping(target = "items", source = "items")
    @Mapping(target = "id", ignore = true)  // ID is auto-generated
    @Mapping(target = "status", constant = "PENDING")  // Default order status
    @Mapping(target = "name", source = "customerName")
    Order toEntity(OrderRequestDTO orderRequest);

    List<OrderResponseDTO> toResponses(List<Order> orders);

    List<Order> toEntities(List<OrderRequestDTO> orderRequests);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")// Include product name
    @Mapping(target = "productDescription", source = "product.description")// Include product description
    @Mapping(target = "price", source = "price")  // Include unit price
    OrderItemResponseDTO toOrderItemResponse(OrderItem orderItem);

    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "id", ignore = true)  // ID is auto-generated
    OrderItem toOrderItemEntity(OrderItemRequestDTO orderItemRequest);

    List<OrderItemResponseDTO> toOrderItemResponses(List<OrderItem> orderItems);

    List<OrderItem> toOrderItemEntities(List<OrderItemRequestDTO> orderItemRequests);
}
