package com.guipossas.orders.controller;

import com.guipossas.orders.domain.OrderItem;
import com.guipossas.orders.dto.OrderItemRequestDto;
import com.guipossas.orders.services.OrderItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/order-items", produces = "application/json")
@RequiredArgsConstructor
public class OrderItemController
{
    private final OrderItemService orderItemService;

    @PostMapping(consumes = "application/json")
    public OrderItem save(@RequestBody @Valid OrderItemRequestDto orderItemRequestDto)
    {
        return orderItemService.save(new OrderItem(orderItemRequestDto));
    }

    @GetMapping(value = "/{id}")
    public OrderItem findById(@PathVariable String id)
    {
        return orderItemService.findById(id);
    }

    @GetMapping
    public List<OrderItem> findAll()
    {
        return orderItemService.findAll();
    }
}
