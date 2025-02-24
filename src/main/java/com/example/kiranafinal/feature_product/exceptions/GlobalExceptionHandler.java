package com.example.kiranafinal.feature_product.exceptions;

import com.example.kiranafinal.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ApiResponse> handleProductException(ProductException ex) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)  // Generic exception handler
    public ResponseEntity<ApiResponse> handleGeneralException(Exception ex) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrorMessage("An unexpected error occurred: " + ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
