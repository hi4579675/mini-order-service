package com.sparta.order.domain.product.entity;

import com.sparta.order.config.error.exception.ApiException;
import com.sparta.order.config.error.status.ErrorStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// JPA는 내부적으로 프록시 객체 생성 시 기본 생성자가 필요한데 public으로 열면 new Product() 처럼 아무데서나 생성이 가능해짐
// -> protected로 막아서 직접 생성 차단, JPA만 사용 가능하게 제한
@SQLRestriction("is_deleted = false")
// // WHERE is_deleted = false 조건이 붙음
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Integer stock;

    @Builder
    // 빌더 직접 쓰는걸 막기 위해 private로 했음
    // 아래 정적 팩토리 메서드(createProduct)를 통해서만 생성하도록 강제
    private Product(String name, Long price, Integer stock){
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // 정적 팩토리 메서드 : new Product() 대신 Product.createProduct()로 생성하게
    // 1. 메서드 이름으로 의도를 명확하게 표현 가능하단 장점이 있음
    // 2. 생성 전 검증 로직, 비즈니스 로직 추가 가능함
    public static Product createProduct(String name, Long price, Integer stock){
        return Product.builder()
                .name(name)
                .price(price)
                .stock(stock)
                .build();
    }

    // 상품 수정을 위한 메서드
    // 엔티티에는 setter 사용 x
    // update()로 캡슐화 하여 수정이라는 행위가 엔티티 안에서만 이뤄지게 하고 싶었음
    public void update(String name, Long price, Integer stock){
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // 재고 차감 메서드
    // Service에서도 할 수 있었지만 재고 차감은 Product의 책임이라고 생각하여 여기에 위치시킴
    public void removeStock(int quantity){
        int restStock = this.stock - quantity;
        if (restStock < 0){
            // 재고 부족 시 커스텀 예외 발생, 서비스 레이어가 아닌 도메인에서 직접 검증
            throw new ApiException(ErrorStatus.LOW_STOCK);
        }
        this.stock = restStock;
    }

    @Column(nullable = false)
    private boolean isDeleted = false;
    // 삭제 여부 플래그
    // false = 정상, true = 삭제됨

    // soft delete 메서드
    public void delete(){
        this.isDeleted = true;
    }
}
