package com.sparta.order.domain.product.controller;

import com.sparta.order.cofing.error.status.SuccessStatus;
import com.sparta.order.common.response.ApiResponse;
import com.sparta.order.domain.product.dto.ProductResponseDto;
import com.sparta.order.domain.product.entity.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Product API", description = "상품 등룩, 수정, 삭제, 조회 기능을 제공합니다.")
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    @Operation(summary = "상품 등록", description = "새로운 상품을 등록합니다. 재고(stock) 정보를 포함해야 합니다. ")
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createProduct(@Valid @RequestBody ProductResponseDto request) {
        return ApiResponse.onSuccess(SuccessStatus.OK, 1L);
    }
    @Operation(summary = "상품 단건 조회", description = "ID를 통해 특정 상품의 상세 정보를 조회합니다.  ")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> getProduct(@PathVariable Long id) {
        return ApiResponse.onSuccess(SuccessStatus.CREATE, "상품 조회 성공: " + id);
    }
}
