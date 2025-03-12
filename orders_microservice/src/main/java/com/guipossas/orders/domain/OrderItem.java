package com.guipossas.orders.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.guipossas.orders.dto.OrderItemRequestDto;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@DynamoDbBean
@Setter
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
    private LocalDateTime createdAt;

    public OrderItem(OrderItemRequestDto orderItemRequestDto)
    {
        this.id = UUID.randomUUID().toString();
        this.name = orderItemRequestDto.name();
        this.price = orderItemRequestDto.price();
        this.quantity = orderItemRequestDto.quantity();
        this.imageUrl = orderItemRequestDto.imageUrl();
        this.description = orderItemRequestDto.description();
        this.createdAt = LocalDateTime.now();
    }

    @DynamoDbAttribute("id")
    @DynamoDbPartitionKey
    public String getId()
    {
        return id;
    }

    @DynamoDbAttribute("orderItemCode")
    public String getOrderItemCode()
    {
        return orderItemCode;
    }

    @DynamoDbAttribute("name")
    public String getName()
    {
        return name;
    }

    @DynamoDbAttribute("price")
    public BigDecimal getPrice()
    {
        return price;
    }

    @DynamoDbAttribute("quantity")
    public Integer getQuantity()
    {
        return quantity;
    }

    @DynamoDbAttribute("imageUrl")
    public String getImageUrl()
    {
        return imageUrl;
    }

    @DynamoDbAttribute("description")
    public String getDescription()
    {
        return description;
    }

    @DynamoDbAttribute("createdAt")
    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }
}
