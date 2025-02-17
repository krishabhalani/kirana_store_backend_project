package com.example.kiranafinal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RateLimiterFilter())
                .addPathPatterns("/v1/api/transactions/**", "/v1/api/orders/checkoutOrder/**", "/v1/api/reports/**")
                .excludePathPatterns("/v1/api/products/**"); // Exclude product catalog API
    }
}
