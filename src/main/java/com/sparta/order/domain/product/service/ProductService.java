package com.sparta.order.domain.product.service;


import com.sparta.order.config.error.exception.ApiException;
import com.sparta.order.config.error.status.ErrorStatus;
import com.sparta.order.domain.product.dto.ProductRequestDto;
import com.sparta.order.domain.product.dto.ProductResponseDto;
import com.sparta.order.domain.product.entity.Product;
import com.sparta.order.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public Long create(ProductRequestDto dto) {
        Product product = Product.createProduct(dto.getName(), dto.getPrice(), dto.getStock());
        // 정적 팩토리 메서드로 생성해서 new Project()직접 호출 불가
        return productRepository.save(product).getId();
    }

    public ProductResponseDto getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorStatus.PRODUCT_NOT_FOUND));
        return ProductResponseDto.from(product);
    }

    @Transactional
    public void update(Long id, ProductRequestDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorStatus.PRODUCT_NOT_FOUND));
        product.update(dto.getName(), dto.getPrice(), dto.getStock());
        //  productRepository.save(product)를 호출하지 않아도 DB에 반영됨!
        // 이유: @Transactional 안에서 조회한 엔티티는 영속성 컨텍스트가 관리함
        // 트랜잭션 종료 시 변경된 필드를 감지(dirty checking)해서 UPDATE 쿼리 자동 실행
    }

    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream() // List를 스트림으로 변환 → 함수형 방식으로 처리
                              .map(ProductResponseDto::from)// 각 Product 엔티티를 ProductResponseDto로 변환
                               .toList(); // 스트림을 다시 List로 수집
    }

    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorStatus.PRODUCT_NOT_FOUND));
        // 존재하지 않는 ID 방어 처리
        product.delete(); // isDeleted = true로 변경

    }
}
