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
public class OrderResponseDto {

    @Schema(description = "주문 ID", example = "1")
    private Long id;

    @Schema(description = "주문 상품명(최신 정보 반영)", example = "아이폰 15 pro")
    private String productName;

    @Schema(description = "주문 수량", example = "1")
    private Integer quantity;

    @Schema(description = "주문 일시")
    private LocalDateTime createdAt;

    // 정적 팩토리 메서드 - 엔티티를 DTO 로 변환
    public static OrderResponseDto from(Order order) {
        return OrderResponseDto.builder()
                .id(order.getId())
                .productName(order.getProduct().getName())
                .quantity(order.getQuantity())
                .createdAt(order.getCreatedAt())
                .build();

    }
}
