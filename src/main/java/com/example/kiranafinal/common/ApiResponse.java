package com.example.kiranafinal.common;

import lombok.Data;
import java.time.Instant;

/**
 * Generic API response wrapper.
 *
 * @param <T> The type of data returned in the response.
 */
@Data
public class ApiResponse<T> {

    /**
     * Status of the API response. Defaults to true (success).
     */
    private Boolean status = true;

    /**
     * Message describing the response.
     */
    private String message;

    /**
     * Timestamp of when the response is created.
     */
    private Instant timestamp = Instant.now();

    /**
     * The actual data returned in the response.
     */
    private T data;

    /**
     * Sets an error message and updates the status to false.
     *
     * @param message The error message to be set.
     */
    public void setErrorMessage(String message) {
        this.status = false;
        this.message = message;
        this.data = null;
    }
}
