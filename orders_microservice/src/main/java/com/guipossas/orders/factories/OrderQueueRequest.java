package com.guipossas.orders.factories;

import com.guipossas.orders.domain.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Component
public class OrderQueueRequest
{
    @Value("${aws.sqs.order-request.url}")
    private String queueUrl;

    public SendMessageRequest createMessageRequest(Order order)
    {
        return SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(order.toString())
                .messageGroupId("orders-ms")
                .messageDeduplicationId(order.getOrderNumber())
                .build();
    }
}
