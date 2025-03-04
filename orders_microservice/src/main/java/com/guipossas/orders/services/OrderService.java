package com.guipossas.orders.services;

import com.guipossas.orders.domain.Order;
import com.guipossas.orders.domain.OrderItem;
import com.guipossas.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService
{
    private final OrderItemService orderItemService;
    private final OrderRepository orderRepository;

    public Order save(Order order)
    {
        List<OrderItem> orderItems = orderItemService.findAll();

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems)
        {
            totalAmount = totalAmount.add(orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        }

        order.setTotal(totalAmount);
        order.setItems(orderItems);

        return orderRepository.save(order);
    }
}
