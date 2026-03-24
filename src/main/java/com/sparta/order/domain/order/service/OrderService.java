package com.sparta.order.domain.order.service;

import com.sparta.order.config.error.exception.ApiException;
import com.sparta.order.config.error.status.ErrorStatus;
import com.sparta.order.domain.order.dto.OrderRequestDto;
import com.sparta.order.domain.order.dto.OrderResponseDto;
import com.sparta.order.domain.order.entity.Order;
import com.sparta.order.domain.order.repository.OrderRepository;
import com.sparta.order.domain.product.entity.Product;
import com.sparta.order.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
// 클래스 레벨에  readOnly = true 설정
// -> 이 서비스의 모든 메서드는 기본적으로 읽기 전용 트랜잭션으로 실행
// JPA의 변경 감지 비활성화
// 부하 분산 가능
// 실수로 데이터 변경하는 것을 방지
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    // 메서드 레벨 @Transactional이 클래스 레벨보다 우선순위 높음
    // create, update, delete 처럼 DB 변경이 필요한 메서드에만 붙임
    public Long createOrder(OrderRequestDto dto) {
        Product product = productRepository.findById(dto.getId())
                .orElseThrow(() -> new ApiException(ErrorStatus.PRODUCT_NOT_FOUND));
        Order order= Order.createOrder(product, dto.getQuantity());
        // 정적 팩토리 메서드로 생성 → 재고 차감도 여기서 같이 처리됨
        // 같은 @Transactional 안에서 실행 → 재고 차감 + 주문 생성이 원자적으로 처리

        return orderRepository.save(order).getId();
        // save() 후 바로 .getId() 체이닝
        // -> 저장된 엔티티의 ID만 반환 (전체 객체 반환 불필요)
    }

    public OrderResponseDto getOrder(Long id) {
        Order order = orderRepository.findByIdWithProduct(id)
                // fetch join 쿼리 → Product 추가 조회 없음
                .orElseThrow(() -> new ApiException(ErrorStatus.ORDER_NOT_FOUND));
        return OrderResponseDto.from(order);
        // 정적 팩토리 메서드로 엔티티 → DTO 변환
    }

    public Page<OrderResponseDto> getOrderPage(Pageable pageable) {
        return orderRepository.findAllWithProduct(pageable)
                .map(OrderResponseDto::from);
        // Page.map(): Page 안의 각 Order를 OrderResponseDto로 변환

    }

    public Slice<OrderResponseDto> getOrderSlice(Pageable pageable) {
        return  orderRepository.findAllWithProductSlice(pageable)
                .map(OrderResponseDto::from);
    }
}
