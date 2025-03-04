package com.guipossas.orders.validation;

import com.guipossas.orders.domain.OrderItem;
import com.guipossas.orders.exceptions.QuantityNotPositive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class IsQuantityPositive implements OrderItemValidator
{
    @Override
    public void validate(OrderItem orderItem)
    {
        if (orderItem.getQuantity() <= 0)
        {
            log.error("INTERRUPTED - quantity {} is not positive for item {}", orderItem.getQuantity(), orderItem.getOrderItemCode());
            throw new QuantityNotPositive();
        }
    }
}
