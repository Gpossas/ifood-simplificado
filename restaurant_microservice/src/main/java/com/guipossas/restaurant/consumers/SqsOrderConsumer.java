package com.guipossas.restaurant.consumers;

import com.guipossas.restaurant.domain.OrderHistory;
import com.guipossas.restaurant.dtos.OrderRequestDto;
import com.guipossas.restaurant.service.OrderService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SqsOrderConsumer implements OrdersConsumer
{
    private final OrderService orderService;

    @SqsListener(value = "orders-placed-queue.fifo")
    @Override
    public void consume(OrderRequestDto order)
    {
        log.info("Received order {}", order.orderNumber());
        orderService.processOrder(new OrderHistory(order));
        log.info("Finished processing order {}", order.orderNumber());
    }
}
