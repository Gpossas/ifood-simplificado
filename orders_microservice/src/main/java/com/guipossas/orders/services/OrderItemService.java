package com.guipossas.orders.services;

import com.guipossas.orders.domain.OrderItem;
import com.guipossas.orders.exceptions.OrderItemNotFound;
import com.guipossas.orders.validation.OrderItemValidationChain;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderItemService
{
    private final DynamoDbTemplate dynamoDbTemplate;
    private final OrderItemValidationChain orderItemValidationChain;

    public OrderItem save(OrderItem orderItem, UUID orderItemCode)
    {
        orderItem.setOrderItemCode(orderItemCode.toString());

        orderItemValidationChain.validate(orderItem);

        log.info("Validated order item {}", orderItemCode);

        OrderItem newOrderItem = dynamoDbTemplate.save(orderItem);

        log.info("Saved order item to database {}", orderItemCode);

        return newOrderItem;
    }

    public OrderItem findById(String id)
    {
        var orderItem = dynamoDbTemplate.load(Key.builder().partitionValue(id).build(), OrderItem.class);

        if (orderItem == null)
        {
            throw new OrderItemNotFound(id);
        }

        return orderItem;
    }

    public void deleteById(String id)
    {
        dynamoDbTemplate.delete(Key.builder().partitionValue(id).build(), OrderItem.class);
    }

    public List<OrderItem> findAll()
    {
        return dynamoDbTemplate.scanAll(OrderItem.class).items().stream().toList();
    }

    public List<OrderItem> findAllIds(List<String> ids)
    {
        List<OrderItem> orderItems = new ArrayList<>();

        for (String id : ids)
        {
            var orderItem = dynamoDbTemplate.load(Key.builder().partitionValue(id).build(), OrderItem.class);

            if (orderItem != null)
            {
                orderItems.add(orderItem);
            }
        }

        return orderItems;
    }
}
