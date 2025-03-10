package com.guipossas.orders.factories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
        try
        {
            String message = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(order);

            return SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(message)
                    .messageGroupId("orders-ms")
                    .messageDeduplicationId(order.getOrderNumber())
                    .build();
        }
        catch (JsonProcessingException e)
        {
            throw new RuntimeException(e);
        }
    }
}
