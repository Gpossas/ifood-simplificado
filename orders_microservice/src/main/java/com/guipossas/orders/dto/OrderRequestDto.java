package com.guipossas.orders.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record OrderRequestDto(
        @NotNull
        List<String> itemsIds,

        @NotNull
        UUID customerId
) {}
