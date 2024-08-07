package com.example.redis_practice.session_clustering;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

    private Set<CartItemDto> items;
    private Date expireAt;

    public static CartDto fromHashPairs(
            Map<String, Integer> entries,
            Date expireAt
    ) {
        return CartDto.builder()
                .items(entries.entrySet().stream()
                        .map(entry -> CartItemDto.builder()
                                .item(entry.getKey())
                                .count(entry.getValue())
                                .build())
                        .collect(Collectors.toUnmodifiableSet()))
                .expireAt(expireAt)
                .build();
    }
}
