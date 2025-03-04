package com.guipossas.orders.dto;


import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OrderItemRequestDto(
        @NotNull
        String name,

        @NotNull
        BigDecimal price,

        @NotNull
        Integer quantity,

        String imageUrl,

        String description
) {}
