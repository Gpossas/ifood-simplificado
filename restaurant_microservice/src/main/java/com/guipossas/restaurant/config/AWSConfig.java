package com.guipossas.restaurant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class AWSConfig
{
    @Bean
    public SqsClient sqs()
    {
        return SqsClient.builder().build();
    }
}
