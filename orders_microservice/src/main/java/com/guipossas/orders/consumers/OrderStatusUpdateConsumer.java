package com.guipossas.orders.consumers;

import com.guipossas.orders.dto.OrderStatusUpdateDto;
import org.springframework.stereotype.Component;

@Component
public interface OrderStatusUpdateConsumer
{
    void consume(OrderStatusUpdateDto orderStatusUpdateDto);
}
