package com.example.redis.controller;

import com.example.redis.domain.ItemDto;
import com.example.redis.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")

public class ItemController {

    private final ItemService itemService;

    @PostMapping("/{id}/purchase")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void purchase(@PathVariable Long id) {
        itemService.purchase(id);
    }

    @GetMapping("/ranks")
    public List<ItemDto> getRanks() {
        return itemService.getMostSold();
    }
}
