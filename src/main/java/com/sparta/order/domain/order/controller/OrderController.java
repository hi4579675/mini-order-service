package com.sparta.order.domain.order.controller;

import com.sparta.order.cofing.error.status.SuccessStatus;
import com.sparta.order.common.response.ApiResponse;
import com.sparta.order.domain.order.dto.OrderRequestDto;
import com.sparta.order.domain.order.dto.OrderResponseDto;
import com.sparta.order.domain.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Order API", description = "주문 관련 API")
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "주문 생성", description = "상품 ID를 이용해 주문을 생성합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createOrder(@Valid @RequestBody OrderRequestDto orderRequestDto) {
        return ApiResponse.onSuccess(SuccessStatus.CREATE, orderService.createOrder(orderRequestDto));
    }

    @Operation(summary = "주문 단건 조회", description = "주문 정보와 함께 최신 상품명을 조회합니다..")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> getOrder(@PathVariable Long id) {
        return ApiResponse.onSuccess(SuccessStatus.OK, orderService.getOrder(id));
    }

}
