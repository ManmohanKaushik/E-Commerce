package com.bikkadit.electronicstore.exception;

import com.bikkadit.electronicstore.payload.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GalobalExceptionHandler {
 private Logger logger=LoggerFactory.getLogger(GalobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException ex) {
        logger.info("ResourceNotFound Exception Handler invoked");
        ApiResponse response = ApiResponse.builder().message(ex.getMessage()).build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        logger.info("handMethodArgumentNotValid Exception Handler invoked ");
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String, Object> response = new HashMap<>();
        allErrors.stream().forEach((objectError) ->
        {
            String massage = objectError.getDefaultMessage();
            String field = ((FieldError) objectError).getField();
            response.put(field, massage);
        });
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse> handBadRequestException(BadRequestException ex) {
        logger.info("Bad Request Exception Handler invoked ");
        ApiResponse response = ApiResponse.builder().message(ex.getMessage()).build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
