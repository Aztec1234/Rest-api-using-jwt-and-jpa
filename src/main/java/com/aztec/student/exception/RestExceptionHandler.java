package com.aztec.student.exception;


import com.aztec.student.exception.custom.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final String INTERNAL_SERVER_ERROR = "Something went wrong!";

    private final String BAD_REQUEST = "Bad request";

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            EntityNotFoundException ex, WebRequest request) {
        //logger.error(".............."+ex.getMessage());
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), toPath(request));
        return buildResponseEntity(apiError);
    }


    @ExceptionHandler(FileStorageException.class)
    protected ResponseEntity<Object> handleFileStorage(
            FileStorageException ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), toPath(request));
        return buildResponseEntity(apiError);
    }



    @ExceptionHandler(EntityAlreadyExistException.class)
    protected ResponseEntity<Object> handleEntityAlreadyExist(
            EntityAlreadyExistException ex, WebRequest request) {
        logger.error(ex.getMessage());
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getMessage(), toPath(request));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleExceptionHandlers(Exception ex, WebRequest request) {
        logger.error(ex.getMessage());
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR, toPath(request));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handle(ConstraintViolationException exception, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Validation errors", toPath(request));
        List<ApiSubError> apiSubErrors = exception.getConstraintViolations()
                .stream()
                .map(message -> {
                    return new ApiValidationError(message.getInvalidValue(), message.getMessage());
                })
                .collect(Collectors.toList());
        apiError.setSubErrors(apiSubErrors);
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    private String toPath(WebRequest webRequest) {
        return ((ServletWebRequest) webRequest).getRequest().getRequestURL().toString();
    }

}