package com.guipossas.orders.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Document(collection = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OrderItem
{
    @Id
    private String id;

    private String name;

    private BigDecimal price;

    private Integer quantity;

    @Field(name = "image_url")
    private String imageUrl;

    private String description;

    @Field(name = "created_at")
    private final OffsetDateTime createdAt = OffsetDateTime.now();
}
