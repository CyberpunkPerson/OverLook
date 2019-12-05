package com.overlook.core.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        BindingResult bindingResult = ex.getBindingResult();
        List validationMessages = bindingResult.getFieldErrors().stream()
                .map(fieldError -> String.format("Wrong value '%s' in field '%s' for object '%s' has been set",
                        fieldError.getRejectedValue(), fieldError.getField(), fieldError.getObjectName()))
                .collect(Collectors.toList());

        return super.handleExceptionInternal(
                ex,
                validationMessages,
                headers,
                status,
                request);
    }

    @ExceptionHandler
    public ResponseEntity<ApiException> handleUndefinedException(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex,
                new ApiException(ex.getMessage()),
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    private ResponseEntity<ApiException> handleExceptionInternal(Exception ex,
                                                                 @Nullable ApiException body,
                                                                 HttpHeaders headers,
                                                                 HttpStatus status,
                                                                 WebRequest request) {

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        ex.printStackTrace();
        return new ResponseEntity<>(body, headers, status);
    }

}
