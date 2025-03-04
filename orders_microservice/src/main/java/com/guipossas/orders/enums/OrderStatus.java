package com.guipossas.orders.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus
{
    WAITING_CONFIRMATION("WAITING_CONFIRMATION"),
    PREPARING("PREPARING"),
    DELIVERED("DELIVERED"),
    CANCELLED("CANCELLED"),
    COMPLETED("COMPLETED");

    private final String status;
}
