package com.guipossas.restaurant.dtos;

import java.time.LocalDateTime;

public record UpdateOrderStatusDto(
    String orderId,
    String orderNumber,
    String status,
    LocalDateTime sentAt,
    LocalDateTime orderedAt
) {}
