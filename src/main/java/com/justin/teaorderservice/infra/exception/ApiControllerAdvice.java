package com.justin.teaorderservice.infra.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<String> handleMessageNotReadableException(HttpMessageNotReadableException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler({NotEnoughStockException.class})
    public ResponseEntity<ResponseError> handleNotEnoughStockException(NotEnoughStockException exception){
        return ResponseEntity.badRequest().body(ResponseError.builder().errorCode(exception.getErrorCode()).build());
    }

    @ExceptionHandler({NotEnoughPointException.class})
    public ResponseEntity<ResponseError> handleNotEnoughPointException(NotEnoughPointException exception){
        return ResponseEntity.badRequest().body(ResponseError.builder().errorCode(exception.getErrorCode()).build());
    }

    @ExceptionHandler({AlreadyCompletedOrderException.class})
    public ResponseEntity<ResponseError> handleAlreadyCompletedOrderException(AlreadyCompletedOrderException exception){
        return ResponseEntity.badRequest().body(ResponseError.builder().errorCode(exception.getErrorCode()).build());
    }

    @ExceptionHandler({ExistEmailException.class})
    public ResponseEntity<ResponseError> handleExistEmailException(ExistEmailException exception){
        return ResponseEntity.badRequest().body(ResponseError.builder().errorCode(exception.getErrorCode()).build());
    }

    @ExceptionHandler({NoExistEmailException.class})
    public ResponseEntity<ResponseError> handleNoExistEmailException(NoExistEmailException exception){
        return ResponseEntity.badRequest().body(ResponseError.builder().errorCode(exception.getErrorCode()).build());
    }

    @ExceptionHandler({NoSuchOrderException.class})
    public ResponseEntity<ResponseError> handleNoSuchOrderException(NoSuchOrderException exception){
        return ResponseEntity.badRequest().body(ResponseError.builder().errorCode(exception.getErrorCode()).build());
    }

    @ExceptionHandler({NoSuchMemberException.class})
    public ResponseEntity<ResponseError> handleNoSuchMemberException(NoSuchMemberException exception){
        return ResponseEntity.badRequest().body(ResponseError.builder().errorCode(exception.getErrorCode()).build());
    }

    @ExceptionHandler({AlreadyNotPendingPointException.class})
    public ResponseEntity<ResponseError> handleAlreadyNotPendingPointException(AlreadyNotPendingPointException exception){
        return ResponseEntity.badRequest().body(ResponseError.builder().errorCode(exception.getErrorCode()).build());
    }

    @ExceptionHandler({NoSuchPointException.class})
    public ResponseEntity<ResponseError> handleNoSuchPointException(NoSuchPointException exception){
        return ResponseEntity.badRequest().body(ResponseError.builder().errorCode(exception.getErrorCode()).build());
    }
}
