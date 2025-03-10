package com.guipossas.restaurant.dtos;

public record UpdateOrderStatusDto(
    String orderId,
    String status
) {}
