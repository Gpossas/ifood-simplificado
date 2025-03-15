package com.guipossas.restaurant.consumers;

import com.guipossas.restaurant.dtos.OrderRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface OrdersConsumer
{
    void consume(OrderRequestDto order);
}
