package com.example.kiranafinal.common;

import lombok.Data;
import java.time.Instant;

@Data
public class ApiResponse<T> {
    private Boolean status = true;  // Default "SUCCESS"
    private String message;
    private Instant timestamp = Instant.now();
    private T data;

    public void setErrorMessage(String message) {
        this.status = false;
        this.message = message;
        this.data = null;
    }
}