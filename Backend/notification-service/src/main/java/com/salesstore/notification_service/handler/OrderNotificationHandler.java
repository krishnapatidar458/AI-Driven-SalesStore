package com.salesstore.notification_service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderNotificationHandler {

    // It listens to the same topic the Order Service is already sending to
    @KafkaListener(topics = "order-placed-topic", groupId = "notification-group")
    public void handleNotification(Object event) {
        log.info("--- NOTIFICATION SERVICE ---");
        log.info("Received event from Kafka: {}", event.toString());
        log.info("Simulating Email: 'Dear Customer, your order has been placed successfully!'");
        log.info("----------------------------");
    }
}