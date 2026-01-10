package com.SalesStore.security;

import com.SalesStore.dto.JwtValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service")
public interface AuthClient {

    @GetMapping("/auth/token/validate")
    JwtValidationResponse validate(@RequestHeader("Authorization") String token);
}

