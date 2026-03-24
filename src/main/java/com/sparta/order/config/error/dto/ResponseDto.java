package com.sparta.order.config.error.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ResponseDto {
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}