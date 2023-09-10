package com.justin.teaorderservice.infra.exception;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseError {
    private ErrorCode errorCode;
    private String target;
}
