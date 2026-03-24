package com.sparta.order.domain.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
    @Schema(description = "상품명", example = "아이폰 15 Pro")
    @NotBlank(message = "상품 명은 필수입니다.")
    private String name;

    @Schema(description = "가격", example = "1500000")
    @NotNull(message = "가격은 필수 입력 항목입니다.")
    @Min(value = 0, message = "가격은 0원 이상이어야 합니다.")
    private Long price;

    @Schema(description = "초기 재고", example = "100")
    @NotNull(message = "초기 재고는 필수 입력 항목입니다.")
    @Min(value = 0, message = "재고는 0개 이상이어야 합니다.")
    private Integer stock;

}
