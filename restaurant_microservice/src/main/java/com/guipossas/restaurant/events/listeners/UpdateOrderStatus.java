package com.guipossas.restaurant.events.listeners;

import com.guipossas.restaurant.dtos.UpdateOrderStatusDto;
import com.guipossas.restaurant.events.OrderReceivedEvent;
import com.guipossas.restaurant.factories.UpdateOrderStatusRequest;
import com.guipossas.restaurant.service.SqsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateOrderStatus
{
    private final SqsService sqsService;
    private final UpdateOrderStatusRequest updateOrderStatusRequest;

    @EventListener
    public void onOrderReceived(OrderReceivedEvent event)
    {
        log.info("Subscriber {} received order {}", this.getClass().getSimpleName(), event.getOrderHistory().getOrderNumber());

        SendMessageRequest updateRequest = updateOrderStatusRequest.createMessageRequest(
                new UpdateOrderStatusDto(
                        event.getOrderHistory().getId(),
                        event.getOrderHistory().getOrderNumber(),
                        "PREPARING"));
        sqsService.publishMessage(updateRequest);

        log.info("Sent order status update {}", event.getOrderHistory().getOrderNumber());
    }
}
