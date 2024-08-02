package com.example.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {


    @Bean
    public RedisTemplate<String, ItemDto> itemRedisTemplate(
            RedisConnectionFactory connectionFactory
            // yml 파일 정보를 보고 Redis DB 에 설정된 ConnectionFactory
    ) {

        RedisTemplate<String, ItemDto> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        // Key 를 Redis 로 넘겨줄 때,
        // 직렬화/역직렬화를 어떤 타입으로 넘기는지 명시
        template.setKeySerializer(RedisSerializer.string());
        // Value 를 Redis 로 넘길 때, Json 형태 넘김
        template.setValueSerializer(RedisSerializer.json());
        return template;
    }


}
