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
    // join fetch: N+1 방지 - Order와 Product를 한 번의 쿼리로 같이 가져옴
    @Query("select o from Order o join fetch o.product where o.id = :id")
    Optional<Order> findByIdWithProduct(@Param("id") Long id);

    //Page 버전
    // countQuery를 분리한 이유:
    // Page는 전체 개수(totalCount)가 필요해서 COUNT 쿼리를 추가 실행함
    // fetch join이 있는 쿼리로 COUNT하면 성능 저하 발생
    // ->  COUNT 전용 쿼리를 따로 분리해서 최적화
    @Query(value = "select  o from Order o join fetch o.product", countQuery = "select count(o) from Order o ")
    Page<Order> findAllWithProduct(Pageable pageable); //

    //Slice 버전 : 전체 개수 없이 "다음 페이지 있는지" 여부만 확인
    // → COUNT 쿼리 없음 → Page보다 빠름
    // 무한 스크롤 구현에 적합
    @Query("select o from Order o join fetch o.product")
    Slice<Order> findAllWithProductSlice(Pageable pageable);

}
