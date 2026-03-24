package com.sparta.order.domain.order.entity;

import com.sparta.order.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders") // SQL 예약어 order와 충돌 방지
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // 주문(N)은 하나의 상품(1)을 참조함 ( N:1 단방향 ) -> @ManyToOne
    // fetch = LAZY : 지연로딩
    //  - EAGER(즉시) : Order 조회 시 Product를 항상 Join해서 가져옴
    //  -- LAZY(지연) : product는 프록시로 두고, 실제로 product.getName()을 호출할 때 쿼리 실행
    // LAZY가 기본 권장값이고, 불필요한 쿼리를 방지한다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    private LocalDateTime createdAt;

    @Builder
    public Order(Product product, Integer quantity, LocalDateTime createdAt) {
        this.product = product;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    // 정적 팩토리 메서드
    // 주문 생성과 재고 차감을 하나의 메서드에서 원자적으로 처리   /  주문 생성에 필요한 작업은 (1. 재고 차감, 2. 주문 생성 )
    public static Order createOrder(Product product, Integer quantity) {
        product.removeStock(quantity);

        return Order.builder()
                .product(product)
                .quantity(quantity)
                .build();
        // 둘다 성공 -> OK
        // 중간에 실패 -> 둘다 롤백, 재고도 원래대로 주문도 저장 안됨
        // 서비스 레이어의 @Transactional이 원자성을 보장해주는 장치다
    }
}
