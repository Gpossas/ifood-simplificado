package com.guipossas.orders.controller;

import com.guipossas.orders.domain.Order;
import com.guipossas.orders.dto.OrderRequestDto;
import com.guipossas.orders.dto.UpdateOrderStatusDto;
import com.guipossas.orders.enums.OrderStatus;
import com.guipossas.orders.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/orders", produces = "application/json")
@RequiredArgsConstructor
@Slf4j
public class OrderController
{
    private final OrderService orderService;

    @PostMapping(consumes = "application/json")
    public Order save(@Valid @RequestBody OrderRequestDto orderRequest)
    {
        UUID orderNumber = UUID.randomUUID();

        log.info("Started processing order {}", orderNumber);

        Order order = orderService.save(new Order(orderNumber, orderRequest.customerId()), orderRequest.itemsIds());

        log.info("Finished processing order {}", orderNumber);

        return order;
    }

    @PatchMapping(value = "/{id}/order_status", consumes = "application/json")
    public void updateOrderStatus(@PathVariable String id, @Valid @RequestBody UpdateOrderStatusDto status)
    {
        orderService.updateOrderStatus(id, OrderStatus.valueOf(status.orderStatus()));
    }
}
