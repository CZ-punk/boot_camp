package com.example.redis_practice.session_clustering;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {

    private String item;
    private Integer count;

}
