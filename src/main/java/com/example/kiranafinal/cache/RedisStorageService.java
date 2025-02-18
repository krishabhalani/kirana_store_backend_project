package com.example.kiranafinal.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Interface for Redis storage operations.
 */
public interface RedisStorageService {

    /**
     * Stores a key-value pair in Redis with an expiration time.
     *
     * @param key The key to store.
     * @param value The value to store.
     * @param ttl Time-to-live (TTL) in seconds.
     */
    void setValueToRedis(String key, String value, long ttl);



    /**
     * Retrieves a value from Redis using the given key.
     *
     * @param key The key to retrieve.
     * @return The value associated with the key, or null if not found.
     */
    String getValueFromRedis(String key);

    /**
     * Checks if a key exists in Redis.
     *
     * @param key The key to check.
     * @return True if the key exists, otherwise false.
     */
    boolean checkKeyExists(String key);


}
