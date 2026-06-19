package com.bitknight.producer_service.Producer.controller;

import com.bitknight.producer_service.Producer.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProducerController {
    private final ProducerService producerService;
    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/publish")
    public String publish(@RequestParam String key, @RequestParam String message) {
        producerService.publish(key, message);
        return "Message published successfully";
    }
}
