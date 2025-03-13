package com.guipossas.orders.consumers;

import com.guipossas.orders.dto.OrderStatusUpdateDto;
import com.guipossas.orders.enums.OrderStatus;
import com.guipossas.orders.services.OrderService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class SqsOrderStatusUpdateConsumer implements OrderStatusUpdateConsumer
{
    private final OrderService orderService;
    @Value("${restaurant_response_time_limit.in.seconds}")
    private int TIME_LIMIT;

    @SqsListener(value = "orders-update-status-queue.fifo")
    @Override
    public void consume(OrderStatusUpdateDto orderStatusUpdateDto)
    {
        log.info("Received order status update {}", orderStatusUpdateDto.orderNumber());

        if (isRestaurantResponseInTime(orderStatusUpdateDto.orderedAt(), orderStatusUpdateDto.sentAt()))
        {
            orderService.updateOrderStatus(orderStatusUpdateDto.orderId(), OrderStatus.valueOf(orderStatusUpdateDto.status()));
            log.info("Updated order status {}", orderStatusUpdateDto.orderNumber());
        }
        else
        {
            log.error("Restaurant took too long to respond for order {}", orderStatusUpdateDto.orderNumber());
        }
    }

    private boolean isRestaurantResponseInTime(LocalDateTime orderedAt, LocalDateTime responseSentAt)
    {
        return responseSentAt.isBefore(orderedAt.plusSeconds(TIME_LIMIT));
    }
}
