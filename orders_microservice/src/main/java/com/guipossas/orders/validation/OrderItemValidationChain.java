package com.guipossas.orders.validation;

import com.guipossas.orders.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class OrderItemValidationChain
{
    private final Set<OrderItemValidator> validators;

    public void validate(OrderItem orderItem)
    {
        validators.forEach(validator -> validator.validate(orderItem));
    }
}
