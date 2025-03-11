package com.guipossas.orders.dto;

public record OrderStatusUpdateDto(
        String orderId,
        String orderNumber,
        String status
) {
}
