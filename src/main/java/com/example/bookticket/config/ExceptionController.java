package com.example.bookticket.config;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class, RuntimeException.class})
    public Object processException(HttpServletRequest req, Exception e) {
        return logAndCreateErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR, req, e, e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object processValidationException(HttpServletRequest req, MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        String message = result.getFieldErrors().stream()
                             .map(er -> er.getField() + ": " + er.getDefaultMessage())
                             .collect(Collectors.joining(" "));
        return logAndCreateErrorInfo(HttpStatus.BAD_REQUEST, req, e, message);
    }

    private Object logAndCreateErrorInfo(HttpStatus status, HttpServletRequest request, Exception e, String message) {
        log.warn("Exception \"{}\" at request: {}, message: {}, cause:", e.getClass().getSimpleName(), request.getRequestURL(), message, getCause(e));
        Map<String, Object> attributes = new LinkedHashMap<>();
        attributes.put("timestamp", new Date());
        attributes.put("status", status.value());
        attributes.put("error", e.getClass().getSimpleName());
        attributes.put("message", e.getMessage());
        attributes.put("path", request.getRequestURI());
        return attributes;
    }

    private Throwable getCause(Throwable throwable) {
        Throwable rootCause = throwable;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }
}
