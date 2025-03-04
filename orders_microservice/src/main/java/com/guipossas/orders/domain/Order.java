package com.guipossas.orders.domain;

import com.guipossas.orders.enums.OrderStatus;
import com.guipossas.orders.enums.PaymentStatus;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Order
{
    private String id;
    private BigDecimal total;
    private List<OrderItem> items = new ArrayList<>();
    private OrderStatus status = OrderStatus.WAITING_CONFIRMATION;
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;
    private final LocalDateTime createdAt = LocalDateTime.now();
}