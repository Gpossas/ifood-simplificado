package com.guipossas.restaurant.events;

import com.guipossas.restaurant.domain.OrderHistory;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OrderReceivedEvent extends ApplicationEvent
{
    private final OrderHistory orderHistory;

    public OrderReceivedEvent(Object source, OrderHistory orderHistory)
    {
        super(source);
        this.orderHistory = orderHistory;
    }
}
