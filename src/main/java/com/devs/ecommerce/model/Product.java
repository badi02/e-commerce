package com.devs.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products", indexes = {
        @Index(columnList = "name"),
        @Index(columnList = "price"),
        @Index(columnList = "category_id")
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    // private String image;

    // @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval =
    // true)
    // private List<ProductImage> images = new ArrayList<>();

    // @ElementCollection
    private List<String> images = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Version // for optimistic locking
    private Long version;
}
