package com.guipossas.orders.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.guipossas.orders.dto.OrderItemRequestDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItem
{
    @Id
    private String id;

    private String orderItemCode;

    private String name;

    private BigDecimal price;

    private Integer quantity;

    @Field(name = "image_url")
    private String imageUrl;

    private String description;

    @Field(name = "created_at")
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
