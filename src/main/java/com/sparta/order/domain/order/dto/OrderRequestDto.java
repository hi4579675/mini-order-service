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
@AllArgsConstructor // 모든 필드를 받는 생성자
@NoArgsConstructor // 기본 생성자 -> Jackson이 JSON을 역직렬화할 때 필요
public class OrderRequestDto {

    @Schema(description = "주문할 상품 ID", example = "1")
    @NotNull(message = "상품 ID는 필수입니다.") //@Valid와 함께 써야 동작?
    private Long id; //Controller에서 @Valid ~ 이렇게 사용

    @Schema(description = "주문 수량", example = "1")
    @NotNull(message = "주문 수량은 필수입니다.")
    @Min(value = 1, message = "최소 1개 이상 주문해야 합니다.")
    private Integer quantity;

}
