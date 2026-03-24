package com.sparta.order.config.error.status;

import com.sparta.order.config.error.dto.ResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", " 유효하지 않은 요청입니다."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT404", "상품을 찾을 수 없습니다."),
    LOW_STOCK(HttpStatus.BAD_REQUEST, "STOCK400", "재고가 부족합니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER404", "주문을 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;


    @Override
    public ResponseDto getReasonHttpStatus() {
        return ResponseDto.builder()
                .httpStatus(httpStatus)
                .code(code)
                .message(message)
                .build();
    }
}
