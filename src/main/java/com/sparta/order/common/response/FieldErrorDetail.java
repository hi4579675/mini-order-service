package com.sparta.order.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class FieldErrorDetail {
    private final String field;
    private final Object rejectedValue;
    private final String message;
}

