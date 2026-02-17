package com.salesstore.notification_service.handler;

import com.salesstore.event.OrderPlacedEvent;
import com.salesstore.notification_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderNotificationHandler {

    private final EmailService emailService;

    @KafkaListener(topics = "order-placed-topic", groupId = "notification-group")
    public void handleNotification(OrderPlacedEvent event) {
        log.info("Received Order Event for Order ID: {}", event.getOrderId());

        // Send the actual email
        emailService.sendOrderConfirmation(event.getEmail(), event.getOrderId());
    }
}