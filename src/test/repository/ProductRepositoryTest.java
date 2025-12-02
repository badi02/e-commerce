package com.devs.ecommerce.repositories;

import com.devs.ecommerce.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void findById_Success() {
        Product product = new Product();
        product.setName("Test");
        entityManager.persist(product);

        Optional<Product> result = productRepository.findById(product.getId());

        assertTrue(result.isPresent());
        assertEquals("Test", result.get().getName());
    }

    // Add more repo tests as needed for other queries
}