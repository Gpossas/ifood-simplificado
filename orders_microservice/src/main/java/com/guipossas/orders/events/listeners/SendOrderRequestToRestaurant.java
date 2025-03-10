package com.guipossas.orders.events.listeners;

import com.guipossas.orders.events.OrderPlacedEvent;
import com.guipossas.orders.factories.OrderQueueRequest;
import com.guipossas.orders.services.SqsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class SendOrderRequestToRestaurant
{
    private final SqsService sqsService;
    private final OrderQueueRequest orderQueueRequest;

    @EventListener
    public void onOrderPlaced(OrderPlacedEvent orderPlacedEvent)
    {
        log.info("Subscriber {} received order placed event {}", this.getClass().getSimpleName(), orderPlacedEvent.getOrder().getOrderNumber());
        sqsService.publishMessage(orderQueueRequest.createMessageRequest(orderPlacedEvent.getOrder()));
        log.info("Sent order {} to restaurant", orderPlacedEvent.getOrder().getOrderNumber());
    }
}
