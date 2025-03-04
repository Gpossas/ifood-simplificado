package com.guipossas.orders.validation;

import com.guipossas.orders.domain.OrderItem;
import com.guipossas.orders.exceptions.PriceNotPositive;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class IsPricePositive implements OrderItemValidator
{
    @Override
    public void validate(OrderItem orderItem)
    {
        if (orderItem.getPrice().compareTo(BigDecimal.ZERO) <= 0)
        {
            throw new PriceNotPositive();
        }
    }
}
