package com.sparta.order.domain.product.dto;

import com.sparta.order.domain.product.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductResponseDto {
    @Schema(description = "상품 ID", example = "1")
    private Long id;

    @Schema(description = "상품명", example = "아이폰 15 pro")
    @NotBlank(message = "상품명은 필수 입력 항목입니다.")
    private String name;

    @Schema(description = "가격", example = "1500000")
    @Min(value = 0, message = "가격은 0원 이상이어야 합니다.")
    private Long price;

    @Schema(description = "초기 재고", example = "100")
    @Min(value = 0, message = "재고는 0개 이상이어야 합니다.")
    private Integer stock;

    @Builder
    public ProductResponseDto(Long id, String name, Long price, Integer stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
    public static ProductResponseDto from(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }
}
