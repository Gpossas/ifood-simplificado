package com.guipossas.orders.dto;

import com.guipossas.orders.enums.OrderStatus;
import com.guipossas.orders.infra.ValidEnum;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderStatusDto(
        @NotNull
        @ValidEnum(enumClass = OrderStatus.class)
        String orderStatus
) {}
