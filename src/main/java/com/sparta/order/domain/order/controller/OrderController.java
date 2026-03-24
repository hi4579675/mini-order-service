package com.sparta.order.domain.order.controller;

import com.sparta.order.config.error.status.SuccessStatus;
import com.sparta.order.common.response.ApiResponse;
import com.sparta.order.domain.order.dto.OrderRequestDto;
import com.sparta.order.domain.order.dto.OrderResponseDto;
import com.sparta.order.domain.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;


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

    // Page 버전
    @Operation(summary = "주문 목록 조회 (Page)", description = "전체 주문 목록을 페이지네이션으로 조회합니다. 총 페이지 수와 전체 개수를 포함합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<OrderResponseDto>>> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderResponseDto> response = orderService.getOrderPage(pageable);
        return ApiResponse.onSuccess(SuccessStatus.OK, response);
    }

    // Slice 버전
    @Operation(summary = "주문 목록 조회 (Slice)", description = "전체 주문 목록을 페이지네이션으로 조회합니다. COUNT 쿼리 없이 다음 페이지 존재 여부만 확인합니다.")
    @GetMapping("/slice")
    public ResponseEntity<ApiResponse<Slice<OrderResponseDto>>> getOrdersSlice(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Slice<OrderResponseDto> response = orderService.getOrderSlice(page, size);
        return ApiResponse.onSuccess(SuccessStatus.OK, response);
    }



}
