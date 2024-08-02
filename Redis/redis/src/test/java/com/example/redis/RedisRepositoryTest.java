package com.example.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void createTest() {
        // 객체 생성
        Item item = Item.builder()
                .name("keyboard")
                .description("Very Expensive Keyboard")
                .price(100000)
                .build();
        // 객체 저장
        Item saveItem = itemRepository.save(item);

    }


    @Test
    public void deleteTest() {
        itemRepository.deleteAll();
    }

}
