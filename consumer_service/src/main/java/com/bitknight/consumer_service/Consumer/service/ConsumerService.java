package com.bitknight.consumer_service.Consumer.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
    @KafkaListener(
            topics = "orders-v2",
            groupId = "group-1"
    )
    public void consume(
            String message,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset
    ) {
        System.out.println("Received : " + message);
        System.out.println("Key : " + key);
        System.out.println("Partition : " + partition);
        System.out.println("Offset : " + offset);
    }
}
