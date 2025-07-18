package com.playtodoo.modulith.sportcomplex.exception;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Locale;
import java.util.stream.Collectors;

@ControllerAdvice(basePackages = "com.playtodoo.modulith.sportcomplex")
@Hidden
public class SportComplexExceptionHandler {

    private final MessageSource messageSource;

    public SportComplexExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex, WebRequest request) {

        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));

        String localizedMessage = getMessage("error.validation.failed", errors);
        return buildErrorResponse(localizedMessage, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(
            Exception ex, WebRequest request) {

        String localizedMessage = getMessage("error.internal");
        return buildErrorResponse(localizedMessage, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<ApiErrorResponse> buildErrorResponse(String message, HttpStatus status, WebRequest request) {
        ApiErrorResponse error = new ApiErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(error, status);
    }

    private String getMessage(String code, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, locale);
    }

    @ExceptionHandler(TypeComplexNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleTypeComplexNotFoundException(
            TypeComplexNotFoundException ex, WebRequest request) {
        String localizedMessage = getMessage("error.type.complex.not.found", ex.getMessage());
        return buildErrorResponse(localizedMessage, HttpStatus.NOT_FOUND, request);
    }
}