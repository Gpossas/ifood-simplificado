package com.guipossas.restaurant.dtos;

import java.math.BigDecimal;

public record OrderItem(
        String orderItemCode,

        String name,

        BigDecimal price,

        Integer quantity,

        String description
) {
}
