package com.example.redis.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.Map;

import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public RedisCacheManager cacheManager(
            RedisConnectionFactory redisConnectionFactory
    ) {
        RedisCacheConfiguration configuration = RedisCacheConfiguration
                .defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofSeconds(120))
                .computePrefixWith(CacheKeyPrefix.simple())
                .serializeValuesWith(
                        SerializationPair.fromSerializer(RedisSerializer.java())
                );

        RedisCacheConfiguration individual = RedisCacheConfiguration
                .defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofSeconds(20))
                .enableTimeToIdle()
                .computePrefixWith(CacheKeyPrefix.simple())
                .serializeValuesWith(
                        SerializationPair.fromSerializer(RedisSerializer.json())
                );

        return RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(configuration)
                .withCacheConfiguration("storeCache", individual)
                .build();
    }
}
