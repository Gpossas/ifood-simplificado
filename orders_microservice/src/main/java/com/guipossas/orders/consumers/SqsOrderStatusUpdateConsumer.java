package com.guipossas.orders.consumers;

import com.guipossas.orders.dto.OrderStatusUpdateDto;
import com.guipossas.orders.enums.OrderStatus;
import com.guipossas.orders.services.OrderService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SqsOrderStatusUpdateConsumer implements OrderStatusUpdateConsumer
{
    private final OrderService orderService;

    @SqsListener(value = "orders-update-status-queue.fifo")
    @Override
    public void consume(OrderStatusUpdateDto orderStatusUpdateDto)
    {
        log.info("Received order status update {}", orderStatusUpdateDto.orderNumber());
        orderService.updateOrderStatus(orderStatusUpdateDto.orderId(), OrderStatus.valueOf(orderStatusUpdateDto.status()));
        log.info("Updated order status {}", orderStatusUpdateDto.orderNumber());
    }
}
