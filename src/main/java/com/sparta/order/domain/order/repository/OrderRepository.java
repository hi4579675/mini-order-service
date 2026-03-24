package com.sparta.order.domain.order.repository;

import com.sparta.order.domain.order.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o join fetch o.product where o.id = :id")
    Optional<Order> findByIdWithProduct(@Param("id") Long id);
}
