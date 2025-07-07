package com.henrique.hbortolim.exception;

import com.henrique.hbortolim.dto.common.ApiResponseDto;
import com.henrique.hbortolim.exception.auth.UserAlreadyExistsException;
import com.henrique.hbortolim.exception.chat.ChatNotFoundException;
import com.henrique.hbortolim.exception.common.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        logger.error("Resource not found: {}", ex.getMessage());
        ApiResponseDto<Object> response = ApiResponseDto.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    @ExceptionHandler(ChatNotFoundException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleChatNotFoundException(
            ChatNotFoundException ex, WebRequest request) {
        logger.error("Chat not found: {}", ex.getMessage());
        ApiResponseDto<Object> response = ApiResponseDto.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleUserAlreadyExistsException(
            UserAlreadyExistsException ex, WebRequest request) {
        logger.error("User already exists: {}", ex.getMessage());
        ApiResponseDto<Object> response = ApiResponseDto.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleValidationException(
            MethodArgumentNotValidException ex, WebRequest request) {
        logger.warn("Validation failed for request: {}", request.getDescription(false));
        
        List<String> errorMessages = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorMessages.add(fieldError.getDefaultMessage());
        }
        
        String message = errorMessages.size() == 1 ? 
            errorMessages.get(0) : 
            "Validation failed: " + String.join(", ", errorMessages);
            
        ApiResponseDto<Object> response = ApiResponseDto.error(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleRuntimeException(
            RuntimeException ex, WebRequest request) {
        logger.error("Runtime exception: {}", ex.getMessage(), ex);
        ApiResponseDto<Object> response = ApiResponseDto.error("Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<Object>> handleGenericException(
            Exception ex, WebRequest request) {
        logger.error("Unexpected error: {}", ex.getMessage(), ex);
        ApiResponseDto<Object> response = ApiResponseDto.error("An unexpected error occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
} 