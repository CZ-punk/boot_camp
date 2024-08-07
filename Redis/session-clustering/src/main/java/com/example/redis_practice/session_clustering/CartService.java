package com.example.redis_practice.session_clustering;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class CartService {

    private final String keyString = "cart:%s";
    private final RedisTemplate<String, String> cartTemplate;
    private final HashOperations<String, String, Integer> hashOps;

    public CartService(RedisTemplate<String, String> cartTemplate, HashOperations<String, String, Integer> hashOps) {
        this.cartTemplate = cartTemplate;
        this.hashOps = cartTemplate.opsForHash();
    }

    public void modifyCart(String sessionId, CartItemDto itemDto) {
        hashOps.increment(
                keyString.formatted(sessionId),
                itemDto.getItem(),
                itemDto.getCount()
        );

        int count = Optional.ofNullable(hashOps.get(keyString.formatted(sessionId), itemDto.getItem())).orElse(0);
        if (count <= 0) {
            // Hash Key 가 전부 사라지면 Hash Table 삭제 된다.
            // Get 할 때, 바구니가 전부 사라져서, getCart 의 !exist 로 404 뜰 수 있다.
            hashOps.delete(keyString.formatted(sessionId), itemDto.getItem());
        }
    }

    public CartDto getCart(String sessionId) {

        boolean exist = Optional.ofNullable(cartTemplate.hasKey(keyString.formatted(sessionId))).orElse(false);

        if (!exist)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        Date expireAt = Date.from(Instant.now().plus(3, ChronoUnit.MINUTES));
        cartTemplate.expireAt(keyString.formatted(sessionId), expireAt);

        return CartDto.fromHashPairs(hashOps.entries(keyString.formatted(sessionId)), expireAt);
    }
}
