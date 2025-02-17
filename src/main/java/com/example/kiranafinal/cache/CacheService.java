package com.example.kiranafinal.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service("cache")
public class CacheService extends RedisStorageServiceImpl {

    @Autowired
    public CacheService(RedisTemplate<String, String> redisTemplate) {
        super(redisTemplate);
    }
}
