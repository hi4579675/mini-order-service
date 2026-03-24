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


    // 주문은 하나의 상품을 참조함 ( N:1 단방향 )
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
    public static Order createOrder(Product product, Integer quantity) {
        // 주문 생성 시점에 상품 재고를 차감함
        product.removeStock(quantity);

        return Order.builder()
                .product(product)
                .quantity(quantity)
                .build();
    }
}
