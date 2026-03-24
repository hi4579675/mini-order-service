package com.sparta.order.cofing.error.status;

import com.sparta.order.cofing.error.dto.ResponseDto;

public interface BaseErrorCode {
    ResponseDto getReasonHttpStatus();
}
