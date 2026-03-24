package com.sparta.order.domain.order.service;

import com.sparta.order.cofing.error.exception.ApiException;
import com.sparta.order.cofing.error.status.ErrorStatus;
import com.sparta.order.domain.order.dto.OrderRequestDto;
import com.sparta.order.domain.order.dto.OrderResponseDto;
import com.sparta.order.domain.order.entity.Order;
import com.sparta.order.domain.order.repository.OrderRepository;
import com.sparta.order.domain.product.entity.Product;
import com.sparta.order.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Long createOrder(OrderRequestDto dto) {
        Product product = productRepository.findById(dto.getId())
                .orElseThrow(() -> new ApiException(ErrorStatus.PRODUCT_NOT_FOUND));
        Order order= Order.createOrder(product, dto.getQuantity());
        return orderRepository.save(order).getId();
    }

    public OrderResponseDto getOrder(Long id) {
        Order order = orderRepository.findByIdWithProduct(id)
                //
                .orElseThrow(() -> new ApiException(ErrorStatus.INVALID_REQUEST));
        return OrderResponseDto.from(order);
    }
}
