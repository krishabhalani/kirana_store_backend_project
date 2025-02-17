package com.example.kiranafinal.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisStorageService {
    void setValueToRedis(String key, String value, long ttl);  // ✅ Set with TTL
    void setValueToRedisWithoutTtl(String key, String value);
    String getValueFromRedis(String key);
    boolean checkKeyExists(String key);
    void expire(String key, Long ttl);
    void del(String key);
}
