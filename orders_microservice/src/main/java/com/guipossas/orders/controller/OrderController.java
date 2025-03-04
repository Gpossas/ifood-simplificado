package com.guipossas.orders.controller;

import com.guipossas.orders.domain.Order;
import com.guipossas.orders.dto.OrderRequestDto;
import com.guipossas.orders.dto.UpdateOrderStatusDto;
import com.guipossas.orders.enums.OrderStatus;
import com.guipossas.orders.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping(value = "/{id}/order_status")
    public void updateOrderStatus(@PathVariable String id, @Valid @RequestBody UpdateOrderStatusDto status)
    {
        orderService.updateOrderStatus(id, OrderStatus.valueOf(status.orderStatus()));
    }
}
