package com.guipossas.restaurant.domain;

import com.guipossas.restaurant.dtos.OrderItem;
import com.guipossas.restaurant.dtos.OrderRequestDto;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "orders_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OrderHistory
{
    private String id;
    private String orderNumber;
    private String customerId;
    private BigDecimal total;
    private List<OrderItem> items = new ArrayList<>();
    private LocalDateTime orderPlacedAt;
    private final LocalDateTime createdAt = LocalDateTime.now();

    public OrderHistory(OrderRequestDto order)
    {
        this.orderNumber = order.orderNumber();
        this.customerId = order.customerId();
        this.total = order.total();
        this.items = order.items();
        this.orderPlacedAt = order.createdAt();
    }
}
