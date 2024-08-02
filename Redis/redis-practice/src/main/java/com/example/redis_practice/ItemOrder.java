package com.example.redis_practice;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@Builder
@RedisHash("itemOrder")
public class ItemOrder implements Serializable {

    @Id
    private String id;
    private String item;
    private Integer quantity;
    private Long totalPrice;
    private String orderStatus;

}
