package com.sparta.order.config.error.status;

import com.sparta.order.config.error.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {
        OK(HttpStatus.OK, "COMMON200", "성공입니다. "),
        CREATE(HttpStatus.CREATED, "COMMON201", "저장에 성공하였습니다.");

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
