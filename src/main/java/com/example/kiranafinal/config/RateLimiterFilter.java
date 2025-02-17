package com.example.kiranafinal.config;

import io.github.bucket4j.*;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiterFilter implements HandlerInterceptor {

    Bucket bucket = getBucket();

    private Bucket getBucket() {
        return Bucket.builder()
                        .addLimit(Bandwidth.classic(10, Refill.greedy(10, Duration.ofMinutes(1))))
                        .build();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        System.out.println(bucket.getAvailableTokens());

        if (bucket.tryConsume(1)) {
            return true; //  Allowed
        } else {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Rate limit exceeded. Try again later.");
            return false; //  Blocked due to rate limit
        }
    }
}
