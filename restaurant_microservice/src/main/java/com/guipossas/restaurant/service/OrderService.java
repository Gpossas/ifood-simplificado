package com.guipossas.restaurant.service;

import com.guipossas.restaurant.domain.OrderHistory;
import com.guipossas.restaurant.events.OrderReceivedEvent;
import com.guipossas.restaurant.repository.OrderHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService
{
    private final OrderHistoryRepository orderHistoryRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public void processOrder(OrderHistory order)
    {
        log.info("Started processing order {}", order.getOrderNumber());

        orderHistoryRepository.save(order);
        log.info("Saved order to database {}", order.getOrderNumber());

        applicationEventPublisher.publishEvent(new OrderReceivedEvent(this, order));
    }
}
