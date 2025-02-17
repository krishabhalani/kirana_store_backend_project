package com.example.kiranafinal.cache;

import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Primary  // ✅ Ensures this is the default Redis Storage implementation
public class RedisStorageServiceImpl implements RedisStorageService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisStorageServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void setValueToRedis(String key, String value, long ttl) {
        redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.SECONDS);
    }

    @Override
    public void setValueToRedisWithoutTtl(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String getValueFromRedis(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean checkKeyExists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    @Override
    public void expire(String key, Long ttl) {
        redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
    }

    @Override
    public void del(String key) {
        redisTemplate.delete(key);
    }
}
