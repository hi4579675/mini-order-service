package com.sparta.order.domain.product.entity;

import com.sparta.order.cofing.error.exception.ApiException;
import com.sparta.order.cofing.error.status.ErrorStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Integer stock;

    @Builder
    private Product(String name, Long price, Integer stock){
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // 정적 팩토리 메서드 :
    public static Product createProduct(String name, Long price, Integer stock){
        return Product.builder()
                .name(name)
                .price(price)
                .stock(stock)
                .build();
    }

    // 상품 수정을 위한 메서드
    public void update(String name, Long price, Integer stock){
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // 재고 차감
    public void removeStock(int quantity){
        int restStock = this.stock - quantity;
        if (restStock < 0){
            throw new ApiException(ErrorStatus.LOW_STOCK);
        }
        this.stock = restStock;
    }
}
