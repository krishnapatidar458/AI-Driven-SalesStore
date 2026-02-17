package com.salesstore.notification_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
public class HealthCheckController {

    @RequestMapping("/health")
    public String healthCheck() {
        return "Notification Service is up and running!";
    }
}
