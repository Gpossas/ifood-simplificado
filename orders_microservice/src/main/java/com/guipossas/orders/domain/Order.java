package com.guipossas.orders.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.guipossas.orders.enums.OrderStatus;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.UpdateBehavior;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

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
    private List<OrderItem> items;
    private OrderStatus status;
    private LocalDateTime createdAt;

    public Order(UUID orderNumber, UUID customerId)
    {
        this.id = UUID.randomUUID().toString();
        this.orderNumber = orderNumber.toString();
        this.customerId = customerId.toString();
        this.items = new ArrayList<>();
        this.status = OrderStatus.WAITING_CONFIRMATION;
        this.createdAt = LocalDateTime.now();
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

    //todo: this update behavior have no effect using spring cloud dynamodb, update to enhanced dynamodb
    @DynamoDbUpdateBehavior(UpdateBehavior.WRITE_IF_NOT_EXISTS)
    @DynamoDbAttribute("items")
    public List<OrderItem> getItems()
    {
        return items;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "StatusCreatedAtIndex")
    @DynamoDbAttribute("status")
    public OrderStatus getStatus()
    {
        return status;
    }

    @DynamoDbSecondarySortKey(indexNames = "StatusCreatedAtIndex")
    @DynamoDbAttribute("createdAt")
    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }
}