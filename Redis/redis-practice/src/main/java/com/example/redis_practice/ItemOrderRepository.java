package com.example.redis_practice;

import org.springframework.data.repository.CrudRepository;


public interface ItemOrderRepository extends CrudRepository<ItemOrder, String> {
}
