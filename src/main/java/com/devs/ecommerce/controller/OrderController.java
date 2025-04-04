package com.devs.ecommerce.controller;

import com.devs.ecommerce.dto.OrderRequestDTO;
import com.devs.ecommerce.dto.OrderResponseDTO;
import com.devs.ecommerce.model.Order;
import com.devs.ecommerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order Controller", description = "Operations related to orders")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Create a new order", description = "Creates a new order in the e-commerce platform")
    @PostMapping
    //@PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO request){
        OrderResponseDTO orderDTO = orderService.placeOrder(request);
        return ResponseEntity.ok(orderDTO);
    }

    @Operation(summary = "Get all orders", description = "Retrieves all orders")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(@AuthenticationPrincipal UserDetails userDetails){
        List<OrderResponseDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Get order by id", description = "Retrieves order by id")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderResponseDTO> getOrderById(@AuthenticationPrincipal UserDetails userDetails,
                                                         @PathVariable Long orderId){
        OrderResponseDTO order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @Operation(summary = "Update order status", description = "Update order status")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{orderId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateOrderStatus(@AuthenticationPrincipal UserDetails userDetails,
                                                  @RequestParam Order.OrderStatus status,
                                                  @PathVariable Long orderId){

        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "cancel order", description = "Cancel order by updating its status to CANCELED and updating products quantities")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{orderId}/cancel")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> cancelOrder(@AuthenticationPrincipal UserDetails userDetails,
                                            @PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }
}
