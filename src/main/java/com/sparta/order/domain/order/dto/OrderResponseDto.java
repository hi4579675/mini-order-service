package com.sparta.order.domain.order.dto;

import com.sparta.order.domain.order.entity.Order;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
// @NoArgsConstructor 없는 이유:
// ResponseDto는 서버에서 만들어서 내보내는 것 → Jackson 역직렬화 불필요
// → 기본 생성자 필요 없음
public class OrderResponseDto {

    @Schema(description = "주문 ID", example = "1")
    private Long id;

    @Schema(description = "주문 상품 ID", example = "1")
    private Long productId;

    @Schema(description = "주문 상품명(최신 정보 반영)", example = "아이폰 15 pro")
    private String productName;
    // 상품명 변경 시 주문 조회에도 자동으로 최신 이름 반영되게

    @Schema(description = "주문 수량", example = "1")
    private Integer quantity;

    @Schema(description = "주문 일시")
    private LocalDateTime createdAt;

    // 정적 팩토리 메서드 - 엔티티를 DTO 로 변환
    // - 엔티티 → DTO 변환 책임을 DTO 클래스 안에 위치시킴
    // - Service에서 변환 로직이 분산되지 않도록 응집
    // - 사용: OrderResponseDto.from(order)
    public static OrderResponseDto from(Order order) {
        return OrderResponseDto.builder()
                .id(order.getId())
                .productId(order.getProduct().getId())
                .productName(order.getProduct().getName())
                // 여기 LAZY 로딩이라 여기서 실제 Product 쿼리가 실행됨, N+1 문제 주의
                .quantity(order.getQuantity())
                .createdAt(order.getCreatedAt())
                .build();

    }
}
