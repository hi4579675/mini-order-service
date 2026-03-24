package com.sparta.order.domain.product.service;


import com.sparta.order.cofing.error.exception.ApiException;
import com.sparta.order.cofing.error.status.ErrorStatus;
import com.sparta.order.domain.product.dto.ProductResponseDto;
import com.sparta.order.domain.product.entity.Product;
import com.sparta.order.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public Long create(ProductResponseDto dto) {
        Product product = Product.createProduct(dto.getName(), dto.getPrice(), dto.getStock());
        return productRepository.save(product).getId();
    }

    public ProductResponseDto getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorStatus.PRODUCT_NOT_FOUND));
        return ProductResponseDto.from(product);
    }

    @Transactional
    public void update(Long id, ProductResponseDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorStatus.PRODUCT_NOT_FOUND));
        product.update(dto.getName(), dto.getPrice(), dto.getStock());
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
