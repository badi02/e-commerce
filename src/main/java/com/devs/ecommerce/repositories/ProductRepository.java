package com.devs.ecommerce.repositories;

import com.devs.ecommerce.dto.ProductListDTO;
import com.devs.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
        @Query("SELECT new com.devs.ecommerce.dto.ProductListDTO(" +
                        "p.id, p.name, p.description, p.price, p.quantity, " +
                        "p.images, " +
                        "p.category.name, p.brand.name) " +
                        "FROM Product p")
        Page<ProductListDTO> findAllWithoutComments(Pageable pageable);

        @Query("SELECT new com.devs.ecommerce.dto.ProductListDTO(" +
                        "p.id, p.name, p.description, p.price, p.quantity, " +
                        "p.images, " +
                        "p.category.name, p.brand.name) " +
                        "FROM Product p WHERE " +
                        "(:categoryId IS NULL OR p.category.id = :categoryId) " +
                        "AND (:query IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
                        "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%'))) " +
                        "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
                        "AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
        Page<ProductListDTO> filterProduct(Pageable pageable,
                        @Param("categoryId") Long categoryId,
                        @Param("query") String query,
                        @Param("minPrice") BigDecimal minPrice,
                        @Param("maxPrice") BigDecimal maxPrice);
}
