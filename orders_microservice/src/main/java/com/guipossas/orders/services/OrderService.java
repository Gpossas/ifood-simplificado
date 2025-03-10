package com.guipossas.orders.services;

import com.guipossas.orders.domain.Order;
import com.guipossas.orders.domain.OrderItem;
import com.guipossas.orders.enums.OrderStatus;
import com.guipossas.orders.enums.PaymentStatus;
import com.guipossas.orders.events.OrderPlacedEvent;
import com.guipossas.orders.exceptions.OrderNotFound;
import com.guipossas.orders.exceptions.OrderWithEmptyItems;
import com.guipossas.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService
{
    private final OrderItemService orderItemService;
    private final OrderRepository orderRepository;
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

        Order savedOrder = orderRepository.save(order);
        log.info("Saved order to database {}", order.getOrderNumber());

        applicationEventPublisher.publishEvent(new OrderPlacedEvent(this, savedOrder));
        log.info("Published order placed event {}", order.getOrderNumber());

        return savedOrder;
    }

    public Order findById(String id)
    {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFound(id));
    }

    public List<Order> findAll()
    {
        return orderRepository.findAll();
    }

    public void updatePaymentStatus(String id, PaymentStatus status)
    {
        Order order = findById(id);
        order.setPaymentStatus(status);
        orderRepository.save(order);
    }

    public void updateOrderStatus(String id, OrderStatus status)
    {
        Order order = findById(id);
        order.setStatus(status);
        orderRepository.save(order);
    }
}
