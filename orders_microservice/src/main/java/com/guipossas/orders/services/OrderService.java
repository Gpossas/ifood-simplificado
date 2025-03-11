package com.guipossas.orders.services;

import com.guipossas.orders.domain.Order;
import com.guipossas.orders.domain.OrderItem;
import com.guipossas.orders.enums.OrderStatus;
import com.guipossas.orders.events.OrderPlacedEvent;
import com.guipossas.orders.exceptions.OrderNotFound;
import com.guipossas.orders.exceptions.OrderWithEmptyItems;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService
{
    private final OrderItemService orderItemService;
    private final DynamoDbTemplate dynamoDbTemplate;
    private final ApplicationEventPublisher applicationEventPublisher;

    public Order save(Order order, List<String> orderItemIds)
    {
        List<OrderItem> orderItems = orderItemService.findAllIds(orderItemIds);

        if (orderItems.isEmpty())
        {
            log.error("INTERRUPTED - Order {} has no items", order.getOrderNumber());
            throw new OrderWithEmptyItems();
        }

        log.info("Validated order {}", order.getOrderNumber());

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems)
        {
            totalAmount = totalAmount.add(orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        }

        order.setTotal(totalAmount);
        order.setItems(orderItems);
        order.setOrderNumber(order.getOrderNumber());

        Order savedOrder = dynamoDbTemplate.save(order);
        log.info("Saved order to database {}", order.getOrderNumber());

        applicationEventPublisher.publishEvent(new OrderPlacedEvent(this, savedOrder));

        return savedOrder;
    }

    public Order findById(String id)
    {
        var order = dynamoDbTemplate.load(Key.builder().partitionValue(id).build(), Order.class);

        if (order == null)
        {
            throw new OrderNotFound(id);
        }

        return order;
    }

    public List<Order> findAll()
    {
        return dynamoDbTemplate.scanAll(Order.class).items().stream().toList();
    }

    public void updateOrderStatus(String id, OrderStatus status)
    {
        Order order = findById(id);
        order.setStatus(status);
        dynamoDbTemplate.update(order);
    }
}
