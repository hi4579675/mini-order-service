package com.sparta.order.cofing.error.exception;

import com.sparta.order.cofing.error.status.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiException extends RuntimeException {
    private final BaseErrorCode errorCode;
}
