package com.guipossas.orders.validation;

import com.guipossas.orders.domain.OrderItem;
import com.guipossas.orders.exceptions.QuantityNotPositive;
import org.springframework.stereotype.Component;

@Component
public class IsQuantityPositive implements OrderItemValidator
{
    @Override
    public void validate(OrderItem orderItem)
    {
        if (orderItem.getQuantity() <= 0)
        {
            throw new QuantityNotPositive();
        }
    }
}
