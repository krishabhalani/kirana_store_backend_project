package com.example.kiranafinal.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Service for caching data using Redis.
 * Extends {@link RedisStorageServiceImpl} to provide Redis caching functionalities.
 */
@Service("cache")
public class CacheService extends RedisStorageServiceImpl {

    /**
     * Constructor to initialize RedisTemplate.
     *
     * @param redisTemplate The RedisTemplate used for cache operations.
     */
    @Autowired
    public CacheService(RedisTemplate<String, String> redisTemplate) {
        super(redisTemplate);
    }
}
