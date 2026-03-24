package com.sparta.order.config.error.exception;

import com.sparta.order.config.error.status.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiException extends RuntimeException {
    private final BaseErrorCode errorCode;
}
