package com.guipossas.orders.services;

import com.guipossas.orders.domain.OrderItem;
import com.guipossas.orders.exceptions.OrderItemNotFound;
import com.guipossas.orders.repository.OrderItemRepository;
import com.guipossas.orders.validation.OrderItemValidationChain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderItemService
{
    private final OrderItemRepository orderItemRepository;
    private final OrderItemValidationChain orderItemValidationChain;

    public OrderItem save(OrderItem orderItem, UUID orderItemCode)
    {
        orderItem.setOrderItemCode(orderItemCode.toString());

        orderItemValidationChain.validate(orderItem);

        log.info("Validated order item {}", orderItemCode);

        OrderItem newOrderItem = orderItemRepository.save(orderItem);

        log.info("Saved order item to database {}", orderItemCode);

        return orderItemRepository.save(orderItem);
    }

    public OrderItem findById(String id)
    {
        return orderItemRepository.findById(id).orElseThrow(() -> new OrderItemNotFound(id));
    }

    public void deleteById(String id)
    {
        orderItemRepository.deleteById(id);
    }

    public List<OrderItem> findAll()
    {
        return orderItemRepository.findAll();
    }

    public List<OrderItem> findAllIds(List<String> ids)
    {
        return orderItemRepository.findAllIds(ids);
    }
}
