package com.example.kiranafinal.cache;

import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Implementation of Redis storage service for caching operations.
 */
@Service
@Primary  // Ensures this is the default Redis Storage implementation
public class RedisStorageServiceImpl implements RedisStorageService {

    private final RedisTemplate<String, String> redisTemplate;

    /**
     * Constructor to initialize RedisTemplate.
     *
     * @param redisTemplate The Redis template for performing operations.
     */
    public RedisStorageServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Stores a key-value pair in Redis with a time-to-live (TTL).
     *
     * @param key   The key to store the value under.
     * @param value The value to be stored.
     * @param ttl   The time-to-live in seconds.
     */
    @Override
    public void setValueToRedis(String key, String value, long ttl) {
        redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.SECONDS);
    }


    /**
     * Retrieves a value from Redis using the provided key.
     *
     * @param key The key to look up in Redis.
     * @return The value associated with the key, or null if not found.
     */
    @Override
    public String getValueFromRedis(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * Checks if a key exists in Redis.
     *
     * @param key The key to check.
     * @return true if the key exists, false otherwise.
     */
    @Override
    public boolean checkKeyExists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }


}
