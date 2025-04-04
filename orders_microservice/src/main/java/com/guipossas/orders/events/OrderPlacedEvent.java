package com.guipossas.orders.events;


import com.guipossas.orders.domain.Order;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OrderPlacedEvent extends ApplicationEvent
{
    private final Order order;

    public OrderPlacedEvent(Object source, Order order)
    {
        super(source);
        this.order = order;
    }
}
