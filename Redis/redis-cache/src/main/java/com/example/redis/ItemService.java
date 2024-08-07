package com.example.redis;

import com.example.redis.domain.Item;
import com.example.redis.domain.ItemDto;
import com.example.redis.repo.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @CachePut(cacheNames = "itemCache", key = "#result.id")
    public ItemDto create(ItemDto dto) {
        return ItemDto.fromEntity(itemRepository.save(Item.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .build()));
    }

    // 이 메서드의 결과는 캐싱이 가능하다.
    // args[0]: SpringEL 로, 인자 중 첫번째를 지정
    // cacheNames: 해당 메서드로 인해서 만들어질 캐시를 지칭하는 이름
    // Key: 캐시 데이터를 구분하기 위해 활용하는 값
    // Redis 캐시에서 데이터를 가져오면 해당 메서드를 동작하지 않고, 캐시만 접근해서 반환
    @Cacheable(cacheNames = "itemCache", key = "args[0]")
    public ItemDto readOne(Long id) {
        return itemRepository.findById(id)
                .map(ItemDto::fromEntity)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Cacheable(cacheNames = "itemAllCache", key = "getMethodName()")
    public List<ItemDto> readAll() {
        return itemRepository.findAll()
                .stream()
                .map(ItemDto::fromEntity)
                .toList();
    }

    @CachePut(cacheNames = "itemCache", key = "args[0]")
    @CacheEvict(cacheNames = "itemAllCache", key = "'readAll'")
    public ItemDto update(Long id, ItemDto dto) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setPrice(dto.getPrice());
        return ItemDto.fromEntity(itemRepository.save(item));
    }


    @CacheEvict(cacheNames = "itemCache" , key = "#args[0]")
//    @CacheEvict(cacheNames = "itemAllCache" , key = "'readAll'")
    public void delete(Long id) {
        itemRepository.deleteById(id);
    }

    @Cacheable(
            cacheNames = "itemSearchCache",
            key = "{ args[0], args[1].pageNumber, args[1].pageSize }"
    )
    public Page<ItemDto> searchByName(String name, Pageable pageable) {
        return itemRepository.findAllByNameContains(name, pageable)
                .map(ItemDto::fromEntity);
    }
}
