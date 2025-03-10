package com.guipossas.restaurant.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderRequestDto(
        String orderNumber,

        String customerId,

        BigDecimal total,

        List<OrderItem>items,

        LocalDateTime createdAt
){}
