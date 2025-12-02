package com.devs.ecommerce.service;

import com.devs.ecommerce.dto.OrderRequestDTO;
import com.devs.ecommerce.dto.OrderResponseDTO;
import com.devs.ecommerce.exception.InsufficientStockException;
import com.devs.ecommerce.exception.ResourceNotFoundException;
import com.devs.ecommerce.mapper.OrderMapper;
import com.devs.ecommerce.model.Order;
import com.devs.ecommerce.model.OrderItem;
import com.devs.ecommerce.model.Product;
import com.devs.ecommerce.model.Town;
import com.devs.ecommerce.repositories.OrderRepository;
import com.devs.ecommerce.repositories.ProductRepository;
import com.devs.ecommerce.repositories.TownRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private TownRepository townRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private OrderRequestDTO orderRequestDTO;
    private OrderResponseDTO orderResponseDTO;
    private Product product;
    private Town town;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setPrice(100.0);
        product.setQuantity(10);

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setQuantity(2);

        order = new Order();
        order.setId(1L);
        order.setName("Customer");
        order.setAddress("Address");
        order.setTown("Town");
        order.setPhoneNumber("123456");
        order.setItems(List.of(orderItem));
        order.setStatus(Order.OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setCustomerName("Customer");
        orderRequestDTO.setAddress("Address");
        orderRequestDTO.setTown("Town");
        orderRequestDTO.setPhoneNumber("123456");
        orderRequestDTO.setItems(List.of(new OrderItemRequestDTO(1L, 2)));

        orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setId(1L);
        // set other fields...

        town = new Town(1L, "Town", 10.0);
    }

    @Test
    void placeOrder_Success() {
        when(orderMapper.toEntity(any(OrderRequestDTO.class))).thenReturn(order);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(townRepository.findByName("Town")).thenReturn(Optional.of(town));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.toResponse(any(Order.class))).thenReturn(orderResponseDTO);

        OrderResponseDTO result = orderService.placeOrder(orderRequestDTO);

        assertNotNull(result);
        verify(orderRepository).save(any(Order.class));
        assertEquals(8, product.getQuantity()); // 10 - 2
    }

    @Test
    void placeOrder_InsufficientStock() {
        product.setQuantity(1); // Less than requested 2

        when(orderMapper.toEntity(any(OrderRequestDTO.class))).thenReturn(order);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertThrows(InsufficientStockException.class, () -> orderService.placeOrder(orderRequestDTO));
    }

    @Test
    void placeOrder_TownNotFound() {
        when(orderMapper.toEntity(any(OrderRequestDTO.class))).thenReturn(order);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(townRepository.findByName("Town")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.placeOrder(orderRequestDTO));
    }

    @Test
    void cancelOrder_Success() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        orderService.cancelOrder(1L);

        assertEquals(Order.OrderStatus.CANCELED, order.getStatus());
        verify(orderRepository).save(order);
        assertEquals(12, product.getQuantity()); // 10 + 2
    }

    @Test
    void cancelOrder_AlreadyCanceled() {
        order.setStatus(Order.OrderStatus.CANCELED);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        assertThrows(IllegalStateException.class, () -> orderService.cancelOrder(1L));
    }

    @Test
    void getOrderById_Success() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderMapper.toResponse(any(Order.class))).thenReturn(orderResponseDTO);

        OrderResponseDTO result = orderService.getOrderById(1L);

        assertNotNull(result);
    }

    @Test
    void getOrderById_NotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(1L));
    }

    @Test
    void getAllOrders_Success() {
        List<Order> orders = List.of(order);
        when(orderRepository.findAll()).thenReturn(orders);
        when(orderMapper.toResponses(orders)).thenReturn(List.of(orderResponseDTO));

        List<OrderResponseDTO> result = orderService.getAllOrders();

        assertEquals(1, result.size());
    }

    @Test
    void updateOrderStatus_Success() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        orderService.updateOrderStatus(1L, Order.OrderStatus.SHIPPED);

        assertEquals(Order.OrderStatus.SHIPPED, order.getStatus());
        verify(orderRepository).save(order);
    }

    @Test
    void updateOrderStatus_NotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> orderService.updateOrderStatus(1L, Order.OrderStatus.SHIPPED));
    }
}