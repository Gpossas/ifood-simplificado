package com.guipossas.orders.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.guipossas.orders.enums.OrderStatus;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@DynamoDbBean
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order
{
    private String id;
    private String orderNumber;
    private String customerId;
    private BigDecimal total;
    private List<OrderItem> items = new ArrayList<>();
    private OrderStatus status = OrderStatus.WAITING_CONFIRMATION;
    private final LocalDateTime createdAt = LocalDateTime.now();

    public Order(UUID orderNumber, UUID customerId)
    {
        this.id = UUID.randomUUID().toString();
        this.orderNumber = orderNumber.toString();
        this.customerId = customerId.toString();
    }

    @DynamoDbAttribute("id")
    @DynamoDbPartitionKey
    public String getId()
    {
        return id;
    }

    @DynamoDbAttribute("orderNumber")
    public String getOrderNumber()
    {
        return orderNumber;
    }

    @DynamoDbAttribute("customerId")
    public String getCustomerId()
    {
        return customerId;
    }

    @DynamoDbAttribute("total")
    public BigDecimal getTotal()
    {
        return total;
    }

    @DynamoDbAttribute("items")
    public List<OrderItem> getItems()
    {
        return items;
    }

    @DynamoDbAttribute("status")
    public OrderStatus getStatus()
    {
        return status;
    }

    @DynamoDbAttribute("createdAt")
    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }
}