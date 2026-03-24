package com.sparta.order.cofing.error.exception;

import com.sparta.order.cofing.error.status.ErrorStatus;
import com.sparta.order.common.response.ApiResponse;
import com.sparta.order.common.response.FieldErrorDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /*
    * [흐름 정리]
    *  1. 애플리케이션 어디서든 ApiException이 발생하면 이 메서드가 호출
    *  2. 예외 객체 e 안에 담긴 ErrorStatus(errorCode)를 꺼냄
    *  3. ApiResonse.onFailure()를 통해 규격화된 응답을 생성함.
    * */

    //비즈니스 커스텀 예외 처리
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Void>> handleApiException(ApiException e) {
        return ApiResponse.onFailure(e.getErrorCode(), null);
        // 얘는 ErrorStatus.PRODUCT_NOT_FOUND 등을 반환함
    }

    // @Vaild 유효성 검사 실패 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<List<FieldErrorDetail>>> handleValidationException(MethodArgumentNotValidException ex) {

        List<FieldErrorDetail> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> FieldErrorDetail.of(
                        error.getField(),
                        error.getRejectedValue(),
                        error.getDefaultMessage()
                ))
                .toList();
        return ResponseEntity.status(ErrorStatus.INVALID_REQUEST.getHttpStatus())
                .body(new ApiResponse<>(
                        false,
                        ErrorStatus.INTERNAL_SERVER_ERROR.getCode(),
                        ErrorStatus.INTERNAL_SERVER_ERROR.getMessage(),
                        fieldErrors
                ));
    }
}
