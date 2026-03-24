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
    // ResponseDto는 서버가 만들어서 내보내는 것 → 부가적인 어노테이션 다 삭제함 @Min @Notnull 이런것
    @Schema(description = "상품 ID", example = "1")
    private Long id;

    @Schema(description = "상품명", example = "아이폰 15 pro")
    private String name;

    @Schema(description = "가격", example = "1500000")
    private Long price;

    @Schema(description = "초기 재고", example = "100")
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
