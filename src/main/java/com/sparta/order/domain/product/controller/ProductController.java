package com.sparta.order.domain.product.controller;

import com.sparta.order.config.error.status.SuccessStatus;
import com.sparta.order.common.response.ApiResponse;
import com.sparta.order.domain.product.dto.ProductRequestDto;
import com.sparta.order.domain.product.dto.ProductResponseDto;
import com.sparta.order.domain.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product API", description = "상품 등룩, 수정, 삭제, 조회 기능을 제공합니다.")
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "상품 등록", description = "새로운 상품을 등록합니다. 재고(stock) 정보를 포함해야 합니다. ")
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createProduct(@Valid @RequestBody ProductRequestDto request) {
        Long id = productService.create(request);
        return ApiResponse.onSuccess(SuccessStatus.CREATE, id);
    }
    @Operation(summary = "상품 단건 조회", description = "ID를 통해 특정 상품의 상세 정보를 조회합니다.  ")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProduct(@PathVariable Long id) {
        ProductResponseDto response = productService.getProduct(id);
        return ApiResponse.onSuccess(SuccessStatus.OK, response);
    }

    //3. 상품 목록 조회
    @Operation(summary = "상품 목록 조회", description = "등록된 모든 상품의 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getAllProducts() {
        List<ProductResponseDto> response = productService.getAllProducts();
        return ApiResponse.onSuccess(SuccessStatus.OK, response);
    }

    //4. 상품 수정
    @Operation(summary = "상품 수정", description = "기존 상품의 정보를 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDto request) {
        productService.update(id, request);
        return ApiResponse.onSuccess(SuccessStatus.OK, "상품 수정 완료");
    }

    // 5. 상품 삭제
    @Operation(summary = "상품 삭제", description = "특정 상품을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ApiResponse.onSuccess(SuccessStatus.OK, "상품 삭제 성공");
    }
}
