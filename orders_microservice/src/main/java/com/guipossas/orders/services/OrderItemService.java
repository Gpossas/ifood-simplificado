package com.guipossas.orders.services;

import com.guipossas.orders.domain.OrderItem;
import com.guipossas.orders.exceptions.OrderItemNotFound;
import com.guipossas.orders.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService
{
    private final OrderItemRepository orderItemRepository;

    public OrderItem save(OrderItem orderItem)
    {
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
}
