package com.devs.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /*
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
     */
    //private String guestId; // << NEW
    private String name;
    private String address;
    private String town; // Store town name
    private String phoneNumber;
    private double deliveryTax; // Store delivery tax amount
    private double totalPrice;
    private OrderStatus status;
    public enum OrderStatus {
        PENDING, PREPARING, SHIPPED, DELIVERING, DELIVERED, CANCELED
    }
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();
}
