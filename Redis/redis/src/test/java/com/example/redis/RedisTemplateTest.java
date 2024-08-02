package com.example.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTemplateTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate<String, ItemDto> itemRedisTemplate;

    @Test
    public void stringOpsTest() {

        // 문자열 조작을 위한 클래스
        ValueOperations<String, String> ops
                // 현재 RedisTemplate 에 설정된 타입을 바탕으로 Redis 문자열 조작
                = stringRedisTemplate.opsForValue();
        ops.set("simple key", "simple value");
        System.out.println(ops.get("simple key"));

        SetOperations<String, String> setOps
                = stringRedisTemplate.opsForSet();
        setOps.add("hobbies", "games");
        setOps.add("hobbies", "coding", "algorithm", "doonge");
        System.out.println("setOps = " + setOps.size("hobbies"));

        stringRedisTemplate.expire("hobbies", 10, TimeUnit.SECONDS);
        stringRedisTemplate.delete("simple key");
    }

    @Test
    public void itemRedisTemplateTest() {
        ValueOperations<String, ItemDto> itemOps
                = itemRedisTemplate.opsForValue();

        itemOps.set("my:keyboard", ItemDto.builder()
                        .name("Mechanical Keyboard")
                        .price(2500000)
                        .description("OMG")
                        .build());
        System.out.println(itemOps.get("my:keyboard"));

        itemOps.set("my:mouse", ItemDto.builder()
                        .name("mouse mice")
                        .price(100000)
                        .description("OMGG")
                        .build());
        System.out.println(itemOps.get("my:mouse"));
    }

    @Test
    public void hashOpsTest() {

        HashOperations<String, Object, Object> hashOps = stringRedisTemplate.opsForHash();

        hashOps.put("user:choi", "username", "choi hyun");
        hashOps.put("user:choi", "age", "26");
        hashOps.put("user:choi", "major", "computer science");

        // 해당 Hash Table 에 Field 존재하는가?
        System.out.println(hashOps.hasKey("user:choi", "username"));


        // 해당 Hash Table 의 지정 Filed 의 Value 값 조회
        System.out.println(hashOps.get("user:choi", "username"));

        // 해당 Hash Table 의 모든 Field-Value 조회
        System.out.println(hashOps.entries("user:choi"));
    }


}
