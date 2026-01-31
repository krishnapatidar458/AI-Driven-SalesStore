package com.salesstore.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // Disable CSRF for APIs
                .authorizeExchange(exchange -> exchange

                        // 1. Allow Eureka & Actuator (Infrastructure)
                        .pathMatchers("/eureka/**", "/actuator/**").permitAll()

                        // 2. PUBLIC Access (Browsing Products) - No Token needed
                        .pathMatchers(org.springframework.http.HttpMethod.GET,
                                "api/v1/products/**") .permitAll()
                        .pathMatchers(org.springframework.http.HttpMethod.GET,
                                "api/v1/categories/**") .permitAll()
                        .pathMatchers(org.springframework.http.HttpMethod.GET,
                                "api/v1/variants/**") .permitAll()

                        // 3. RESTRICTED Access (Creating/Editing) - Needs "SELLER" Role
                        .pathMatchers(org.springframework.http.HttpMethod.POST,
                                "/api/v1/products/**").hasRole("SELLER")
                        .pathMatchers(org.springframework.http.HttpMethod.PUT,
                                "/api/v1/products/**").hasRole("SELLER")
                        .pathMatchers(org.springframework.http.HttpMethod.DELETE,
                                "/api/v1/products/**").hasRole("SELLER")

                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                    .jwt(jwt -> jwt.jwtAuthenticationConverter(new KeycloakRoleConverter()))
                );

        return serverHttpSecurity.build();
    }
}
