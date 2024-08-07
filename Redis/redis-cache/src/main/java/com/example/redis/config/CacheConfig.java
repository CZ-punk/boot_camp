package com.example.redis.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {

        // CacheManager 설정 구성 진행
        // Redis 를 이용해서 Spring Cache 를 사용할 때,
        // Redis 관련 설정을 모아두는 클래스
        RedisCacheConfiguration configuration = RedisCacheConfiguration
                .defaultCacheConfig()
                // Null Value 는 캐시 저장 Disable
                .disableCachingNullValues()
                // 기본 캐시 유지 시간 ( Time To Live )
                .entryTtl(Duration.ofSeconds(120))
                // 캐시를 구분하는 접두사 설정
                .computePrefixWith(CacheKeyPrefix.simple())
                // 캐시에 저장할 값을 어떻게 직렬화 / 역직렬화 할 것인지
                .serializeValuesWith(SerializationPair.fromSerializer(RedisSerializer.java()));

        return RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(configuration)
                .build();
    }
}
