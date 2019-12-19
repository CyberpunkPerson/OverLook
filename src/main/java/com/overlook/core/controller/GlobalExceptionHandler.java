package com.overlook.core.controller;

import com.overlook.core.exception.ApiException;
import com.overlook.core.exception.ReportExportException;
import lombok.Builder;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.AuthenticationException;
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


    @ExceptionHandler(ReportExportException.class)
    public ResponseEntity<ApiException> handleReportExportException(ReportExportException ex,
                                                                    WebRequest request) {
        return handleException()
                .exception(ex)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiException(ex.getMessage()))
                .request(request)
                .build();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        BindingResult bindingResult = ex.getBindingResult();
        List<String> validationMessages = bindingResult.getFieldErrors().stream()
                .map(fieldError -> String.format("Wrong value '%s' in field '%s' for object '%s' has been set",
                        fieldError.getRejectedValue(), fieldError.getField(), fieldError.getObjectName()))
                .collect(Collectors.toList());

        return super.handleExceptionInternal(
                ex,
                new ApiException(validationMessages.toString()),
                headers,
                HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiException> handleAuthenticationException(AuthenticationException ex,
                                                                      WebRequest request) {
        return handleException()
        .exception(ex)
        .status(HttpStatus.FORBIDDEN)
        .body(new ApiException(ex.getMessage()))
        .request(request)
        .build();
    }

    @ExceptionHandler
    public ResponseEntity<ApiException> handleUndefinedException(Exception ex, WebRequest request) {
        return handleException()
                .exception(ex)
                .body(new ApiException(ex.getMessage()))
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .request(request)
                .build();

    }

    @Builder(builderMethodName = "handleException")
    private ResponseEntity<ApiException> handleExceptionInternal(@NonNull Exception exception,
                                                                 @Nullable ApiException body,
                                                                 HttpHeaders headers,
                                                                 @NonNull HttpStatus status,
                                                                 @NonNull WebRequest request) {

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, exception, WebRequest.SCOPE_REQUEST);
        }
        exception.printStackTrace();
        return new ResponseEntity<>(body, headers, status);
    }

}
