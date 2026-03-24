package com.sparta.order.domain.order.repository;

import com.sparta.order.domain.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o join fetch o.product where o.id = :id")
    Optional<Order> findByIdWithProduct(@Param("id") Long id);

    //Page 버전
    @Query(value = "select  o from Order o join fetch o.product", countQuery = "select count(o) from Order o ")
    Page<Order> findAllWithProduct(Pageable pageable);

    //Slice 버전
    @Query("select o from Order o join fetch o.product")
    Slice<Order> findAllWithProductSlice(Pageable pageable);

}
