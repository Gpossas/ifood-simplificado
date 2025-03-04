package com.guipossas.orders.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequestDto(
        @NotNull
        List<String> itemsIds
) {}
