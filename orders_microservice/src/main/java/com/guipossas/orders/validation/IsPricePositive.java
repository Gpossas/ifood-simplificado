package com.guipossas.orders.validation;

import com.guipossas.orders.domain.OrderItem;
import com.guipossas.orders.exceptions.PriceNotPositive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class IsPricePositive implements OrderItemValidator
{
    @Override
    public void validate(OrderItem orderItem)
    {
        if (orderItem.getPrice().compareTo(BigDecimal.ZERO) <= 0)
        {
            log.error("INTERRUPTED - Price {} is not positive for item {}", orderItem.getPrice(), orderItem.getOrderItemCode());
            throw new PriceNotPositive();
        }
    }
}
