package com.sparta.order.domain.order.dto;

import com.sparta.order.domain.order.entity.Order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

    @Schema(description = "주문할 상품 ID", example = "1")
    @NotNull(message = "상품 ID는 필수입니다.")
    private Long id;

    @Schema(description = "주문 수량", example = "1")
    @NotNull(message = "주문 수량은 필수입니다.")
    @Min(value = 1, message = "최소 1개 이상 주문해야 합니다.")
    private Integer quantity;

}
