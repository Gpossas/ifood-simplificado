package com.guipossas.orders.services;

import com.guipossas.orders.domain.Order;
import com.guipossas.orders.domain.OrderItem;
import com.guipossas.orders.enums.OrderStatus;
import com.guipossas.orders.enums.PaymentStatus;
import com.guipossas.orders.exceptions.OrderNotFound;
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
