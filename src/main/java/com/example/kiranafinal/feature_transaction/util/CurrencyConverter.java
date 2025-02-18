package com.example.kiranafinal.feature_transaction.util;

import com.example.kiranafinal.cache.RedisStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Utility service for fetching and caching currency exchange rates.
 */
@Service
public class CurrencyConverter {

    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/{baseCurrency}";
    private static final String REDIS_KEY_PREFIX = "exchange_rate:";
    private static final long CACHE_TTL_SECONDS = 3600; // Cache for 1 hour

    private final RestTemplate restTemplate = new RestTemplate();
    private final RedisStorageService redisStorageService;

    /**
     * Constructor for CurrencyConverter.
     *
     * @param redisStorageService Service for caching exchange rates in Redis.
     */
    public CurrencyConverter(RedisStorageService redisStorageService) {
        this.redisStorageService = redisStorageService;
    }

    /**
     * Retrieves the exchange rate between two currencies.
     * Checks the Redis cache first, and if not found, fetches from an external API.
     *
     * @param fromCurrency The base currency.
     * @param toCurrency   The target currency.
     * @return The exchange rate, or null if an error occurs.
     */
    public Double getExchangeRate(String fromCurrency, String toCurrency) {
        String redisKey = REDIS_KEY_PREFIX + fromCurrency + ":" + toCurrency;

        // Check Redis Cache
        System.out.println("Checking Redis Cache for key: " + redisKey);
        if (redisStorageService.checkKeyExists(redisKey)) {
            String cachedRate = redisStorageService.getValueFromRedis(redisKey);
            if (cachedRate != null) {
                System.out.println("Cache HIT: " + redisKey + " = " + cachedRate);
                return Double.parseDouble(cachedRate);
            }
        }

        // Fetch exchange rate from external API
        try {
            System.out.println("Fetching exchange rate from API: " + fromCurrency + " -> " + toCurrency);
            String url = API_URL.replace("{baseCurrency}", fromCurrency);
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null || !response.containsKey("rates")) {
                throw new RuntimeException("API response is invalid");
            }

            Map<String, Object> rates = (LinkedHashMap<String, Object>) response.get("rates");
            if (rates == null || !rates.containsKey(toCurrency)) {
                throw new RuntimeException("Exchange rate not found.");
            }

            Double exchangeRate = Double.valueOf(rates.get(toCurrency).toString());

            // Store exchange rate in Redis cache
            System.out.println("Storing Exchange Rate in Redis: " + redisKey + " = " + exchangeRate);
            redisStorageService.setValueToRedis(redisKey, String.valueOf(exchangeRate), CACHE_TTL_SECONDS);

            return exchangeRate;
        } catch (Exception e) {
            System.err.println("Currency conversion API error: " + e.getMessage());
            return null;
        }
    }
}
