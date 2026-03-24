package com.sparta.order.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.order.config.error.status.BaseCode;
import com.sparta.order.config.error.status.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {
    private final Boolean isSuccess;        // 성공 여부
    private final String code;              // 응답 코드
    private final String message;           // 응답 메시지
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T payload;                // 실제 응답 데이터 (성공 시 포함)

    // 성공 응답
    public static <T> ResponseEntity<ApiResponse<T>> onSuccess(BaseCode code, T payload) {
        // 1. Enum(ErrorStatus)에서 정의한 HTTP 상태, 코드, 메세지를 가져옴
        ApiResponse<T> response = new ApiResponse<>(true, code.getReasonHttpStatus().getCode(), code.getReasonHttpStatus().getMessage(), payload);
        // 2. 최종적으로 HTTP 상태 코드와 함꼐 JSON 바디를 반환
        return ResponseEntity.status(code.getReasonHttpStatus().getHttpStatus()).body(response);
    }

    // 실패 응답
    public static <T> ResponseEntity<ApiResponse<T>> onFailure(BaseErrorCode code, T payload) {
        ApiResponse<T> response = new ApiResponse<>(false, code.getReasonHttpStatus().getCode(), code.getReasonHttpStatus().getMessage(), null);
        return ResponseEntity.status(code.getReasonHttpStatus().getHttpStatus()).body(response);
    }
}
