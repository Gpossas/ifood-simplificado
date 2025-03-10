package com.guipossas.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

@Service
@RequiredArgsConstructor
public class SqsService
{
    private final SqsClient sqsClient;

    public SendMessageResponse publishMessage(SendMessageRequest sendMessageRequest)
    {
        return sqsClient.sendMessage(sendMessageRequest);
    }
}
