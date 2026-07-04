package com.bitknight.producer_service.Producer.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    public ProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(String key, String message) {
        kafkaTemplate.send("orders-v2", key, message).whenComplete(
                (result, ex) -> {
                    if (ex == null) System.out.println(result);
                    else ex.printStackTrace();
                }
        );
    }
}
