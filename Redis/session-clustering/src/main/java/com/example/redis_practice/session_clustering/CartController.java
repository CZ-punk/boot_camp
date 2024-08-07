package com.example.redis_practice.session_clustering;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
@Slf4j
public class CartController {

    private final CartService cartService;
    @PostMapping
    public CartDto modifyCart(@RequestBody CartItemDto itemDto, HttpSession session) {
        cartService.modifyCart(session.getId(), itemDto);
        return cartService.getCart(session.getId());
    }

    @GetMapping
    public CartDto getCart(HttpSession session) {
        log.info("Get Cart Session Id: {}", session.getId());
        return cartService.getCart(session.getId());
    }
}
