package com.guipossas.orders.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.guipossas.orders.dto.OrderItemRequestDto;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItem
{
    private String id;
    private String orderItemCode;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private String imageUrl;
    private String description;
    private final LocalDateTime createdAt = LocalDateTime.now();

    public OrderItem(OrderItemRequestDto orderItemRequestDto)
    {
        this.name = orderItemRequestDto.name();
        this.price = orderItemRequestDto.price();
        this.quantity = orderItemRequestDto.quantity();
        this.imageUrl = orderItemRequestDto.imageUrl();
        this.description = orderItemRequestDto.description();
    }
}
