package com.example.redis_practice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class ItemOrderController {

    private final ItemOrderRepository itemOrderRepository;


    @PostMapping
    public ItemOrder createOrder(@RequestBody ItemOrder itemOrder) {
        return itemOrderRepository.save(itemOrder);
    }

    @GetMapping
    public List<ItemOrder> readAll() {
        return (List<ItemOrder>) itemOrderRepository.findAll();
    }

    @GetMapping("/{id}")
    public ItemOrder readById(@PathVariable String id) {
        return itemOrderRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("no search by id"));
    }

    @PutMapping("/{id}")
    public ItemOrder updateOrder(@PathVariable String id, @RequestBody ItemOrder itemOrder) {

        ItemOrder findOrder = itemOrderRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("no search by id"));

        findOrder.setItem(itemOrder.getItem());
        findOrder.setOrderStatus(itemOrder.getOrderStatus());
        findOrder.setQuantity(itemOrder.getQuantity());
        findOrder.setTotalPrice(itemOrder.getTotalPrice());

        return itemOrderRepository.save(findOrder);
    }


    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable String id) {
        itemOrderRepository.deleteById(id);
    }




}


