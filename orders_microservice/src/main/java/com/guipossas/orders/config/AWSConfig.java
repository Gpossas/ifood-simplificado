package com.guipossas.orders.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class AWSConfig
{
    @Bean
    public SqsClient sqs()
    {
        return SqsClient.builder().build();
    }

    @Bean
    public DynamoDbClient dynamoDb()
    {
        return DynamoDbClient.builder().build();
    }
}
