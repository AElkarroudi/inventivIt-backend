package com.inventivIt.dreamCase.controller;

import com.inventivIt.dreamCase.util.apiResponse.fail.FailDTO;
import com.inventivIt.dreamCase.util.apiResponse.fail.FailType;
import com.inventivIt.dreamCase.util.exception.ResourceNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FailDTO handleBadRequestException(BadRequestException ex) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("error", ex.getMessage());

        return new FailDTO(
                FailType.BAD_REQUEST,
                errors
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FailDTO handleValidationExceptions(final MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new FailDTO(
                FailType.INVALID_DATA,
                errors
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public FailDTO handleMissingRequestBody(HttpMessageNotReadableException ex) {
        return new FailDTO(
                FailType.MISSING_BODY_OR_UNREADABLE,
                "Request body is missing or unreadable"
        );
    }

    @ExceptionHandler({
            ResourceNotFoundException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    FailDTO handleBadRequestException(Exception ex) {
        return new FailDTO(
                FailType.CUSTOM_EXCEPTION_HANDLER,
                ex.getMessage()
        );
    }

}
