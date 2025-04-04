package com.devs.ecommerce.repositories;

import com.devs.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    //List<Order> findByUserId(Long userId);
}
