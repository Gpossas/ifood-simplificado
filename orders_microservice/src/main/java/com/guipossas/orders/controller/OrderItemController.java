package com.guipossas.orders.controller;

import com.guipossas.orders.domain.OrderItem;
import com.guipossas.orders.dto.OrderItemRequestDto;
import com.guipossas.orders.services.OrderItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/order-items", produces = "application/json")
@RequiredArgsConstructor
@Slf4j
public class OrderItemController
{
    private final OrderItemService orderItemService;

    @PostMapping(consumes = "application/json")
    public OrderItem save(@RequestBody @Valid OrderItemRequestDto orderItemRequestDto)
    {
        UUID orderItemCode = UUID.randomUUID();

        log.info("Started processing order item {}", orderItemCode);

         OrderItem orderItem = orderItemService.save(new OrderItem(orderItemRequestDto), orderItemCode);

        log.info("Finished processing order item {}", orderItemCode);

        return orderItem;
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

    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable String id)
    {
        orderItemService.deleteById(id);
    }
}
