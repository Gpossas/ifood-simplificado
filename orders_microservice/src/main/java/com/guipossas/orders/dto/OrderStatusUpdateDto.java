package com.guipossas.orders.dto;

import java.time.LocalDateTime;

public record OrderStatusUpdateDto(
        String orderId,
        String orderNumber,
        String status,
        LocalDateTime sentAt,
        LocalDateTime orderedAt
) {
}
