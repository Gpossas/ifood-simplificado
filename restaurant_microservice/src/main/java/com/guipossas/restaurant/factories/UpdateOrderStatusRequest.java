package com.guipossas.restaurant.factories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.guipossas.restaurant.dtos.UpdateOrderStatusDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Component
public class UpdateOrderStatusRequest
{
    @Value("${aws.sqs.order_status_update.url}")
    private String queueUrl;

    public SendMessageRequest createMessageRequest(UpdateOrderStatusDto orderStatusDto)
    {
        try
        {
            String message = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(orderStatusDto);

            return SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(message)
                    .messageGroupId("restaurant-ms")
                    .messageDeduplicationId(orderStatusDto.orderId())
                    .build();
        }
        catch (JsonProcessingException e)
        {
            throw new RuntimeException(e);
        }
    }
}
