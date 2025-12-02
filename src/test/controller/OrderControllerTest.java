package com.devs.ecommerce.controller;

import com.devs.ecommerce.dto.OrderRequestDTO;
import com.devs.ecommerce.dto.OrderResponseDTO;
import com.devs.ecommerce.model.Order;
import com.devs.ecommerce.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void createOrder_Success() throws Exception {
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        // set fields...

        when(orderService.placeOrder(any(OrderRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerName\":\"Test\"}")) // adjust JSON
                .andExpect(status().isOk());
    }

    @Test
    void getAllOrders_Success() throws Exception {
        when(orderService.getAllOrders()).thenReturn(List.of(new OrderResponseDTO()));

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk());
    }

    @Test
    void getOrderById_Success() throws Exception {
        when(orderService.getOrderById(anyLong())).thenReturn(new OrderResponseDTO());

        mockMvc.perform(get("/api/orders/{orderId}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void updateOrderStatus_Success() throws Exception {
        doNothing().when(orderService).updateOrderStatus(anyLong(), any(Order.OrderStatus.class));

        mockMvc.perform(put("/api/orders/{orderId}/status", 1L)
                .param("status", "SHIPPED"))
                .andExpect(status().isOk());
    }

    @Test
    void cancelOrder_Success() throws Exception {
        doNothing().when(orderService).cancelOrder(anyLong());

        mockMvc.perform(put("/api/orders/{orderId}/cancel", 1L))
                .andExpect(status().isOk());
    }
}