package com.guipossas.orders.events.listeners;

import com.guipossas.orders.events.OrderPlacedEvent;
import com.guipossas.orders.factories.OrderQueueRequest;
import com.guipossas.orders.services.SqsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendOrderRequestToRestaurant
{
    private final SqsService sqsService;
    private final OrderQueueRequest orderQueueRequest;

    public void onOrderPlaced(OrderPlacedEvent orderPlacedEvent)
    {
        sqsService.publishMessage(orderQueueRequest.createMessageRequest(orderPlacedEvent.getOrder()));
    }
}
