package com.guipossas.orders.validation;

import com.guipossas.orders.domain.OrderItem;
import org.springframework.stereotype.Component;

@Component
public interface OrderItemValidator
{
    void validate(OrderItem orderItemRequestDto);
}
