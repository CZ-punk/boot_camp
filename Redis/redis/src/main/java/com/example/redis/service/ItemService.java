package com.example.redis.service;

import com.example.redis.domain.ItemDto;
import com.example.redis.domain.Item;
import com.example.redis.domain.ItemOrder;
import com.example.redis.repository.ItemOrderRepository;
import com.example.redis.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemOrderRepository orderRepository;
    private final ZSetOperations<String, ItemDto> rankOps;


    public ItemService(ItemRepository itemRepository, ItemOrderRepository orderRepository, RedisTemplate<String, ItemDto> template) {
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
        this.rankOps = template.opsForZSet();
    }

    public void purchase(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        ItemOrder order = new ItemOrder();
        order.addItem(item);
        orderRepository.save(order);

        ItemDto dto = ItemDto.fromEntity(item);
        rankOps.incrementScore("soldRanks", dto, 1);
    }

    public List<ItemDto> getMostSold() {
        Set<ItemDto> ranks = rankOps.reverseRange("soldRanks", 0, 9);
        if (ranks == null) return Collections.emptyList();
        return ranks.stream().toList();
    }
}
