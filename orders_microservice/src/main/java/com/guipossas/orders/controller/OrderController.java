package com.guipossas.orders.controller;

import com.guipossas.orders.domain.Order;
import com.guipossas.orders.dto.OrderRequestDto;
import com.guipossas.orders.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/orders", produces = "application/json")
@RequiredArgsConstructor
public class OrderController
{
    private final OrderService orderService;

    @PostMapping(consumes = "application/json")
    public Order save(@Valid @RequestBody OrderRequestDto orderRequest)
    {
        return orderService.save(new Order(), orderRequest.itemsIds());
    }
}
