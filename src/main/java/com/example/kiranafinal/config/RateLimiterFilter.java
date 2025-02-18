package com.example.kiranafinal.config;


import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import java.io.IOException;
import java.time.Duration;

/**
 * A filter that applies rate limiting to incoming HTTP requests.
 * Uses the Bucket4j library to restrict request rates.
 */
public class RateLimiterFilter implements HandlerInterceptor {

    // Token bucket to track request limits
    Bucket bucket = getBucket();

    /**
     * Creates and configures a token bucket with a limit of 10 requests per minute.
     *
     * @return A configured Bucket instance.
     */
    private Bucket getBucket() {
        return Bucket.builder()
                .addLimit(Bandwidth.classic(10, Refill.greedy(10, Duration.ofMinutes(1))))
                .build();
    }

    /**
     * Intercepts HTTP requests and applies rate limiting.
     *
     * @param request  The incoming HTTP request.
     * @param response The HTTP response.
     * @param handler  The request handler.
     * @return true if the request is allowed, false if rate limit is exceeded.
     * @throws IOException if an error occurs while writing the response.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        System.out.println(bucket.getAvailableTokens());

        if (bucket.tryConsume(1)) {
            return true; // Request allowed
        } else {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Rate limit exceeded. Try again later.");
            return false; // Request blocked due to rate limit
        }
    }
}
