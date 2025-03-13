package com.guipossas.orders.services;

import com.guipossas.orders.domain.Order;
import com.guipossas.orders.enums.OrderStatus;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService
{
    private final DynamoDbTemplate dynamoDbTemplate;
    @Value("${restaurant_response_time_limit.in.seconds}")
    private int TIME_LIMIT;

    @Scheduled(
            initialDelayString = "${initial_delay.in.seconds}",
            fixedDelayString = "${restaurant_response_time_limit.in.seconds}",
            timeUnit = java.util.concurrent.TimeUnit.SECONDS)
    public void clearOrdersNoRestaurantResponse()
    {
        log.info("JOB - Started clearing orders with no response from restaurant {}", LocalDateTime.now());

        LocalDateTime timeLimit = LocalDateTime.now().minusSeconds(TIME_LIMIT);

        QueryConditional condition = QueryConditional.sortGreaterThan(Key.builder()
                    .partitionValue(OrderStatus.WAITING_CONFIRMATION.name())
                    .sortValue(timeLimit.toString())
                    .build());
        QueryEnhancedRequest request = QueryEnhancedRequest.builder()
                .queryConditional(condition)
                .build();
        List<Order> ordersToCancel = dynamoDbTemplate.query(request, Order.class, "StatusCreatedAtIndex")
                    .stream().flatMap(orderPage -> orderPage.items().stream()).toList();

        for (Order order : ordersToCancel)
        {
            order.setStatus(OrderStatus.CANCELLED);
            dynamoDbTemplate.update(order);
            log.info("JOB - Cancelled order {}", order.getOrderNumber());
        }

        log.info("JOB - Finished clearing orders with no response from restaurant {}", LocalDateTime.now());
    }
}
