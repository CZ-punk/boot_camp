package com.example.redis_practice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ValueOperations<String, Integer> ops;

    private ArticleController(RedisTemplate<String, Integer> template) {
        ops = template.opsForValue();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void read(@PathVariable Long id) {
        ops.increment("articles:%d".formatted(id));
    }
}
