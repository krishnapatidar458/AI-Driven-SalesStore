package com.salesstore.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(
            ServerHttpSecurity http
    ) {

        http.csrf(ServerHttpSecurity.CsrfSpec::disable)

                .authorizeExchange(exchange -> exchange

                        .pathMatchers("/eureka/**", "/actuator/**").permitAll()

                        .pathMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/v1/variants/**").permitAll()

                        .pathMatchers(HttpMethod.GET, "/api/v1/orders/**").permitAll()
                        .pathMatchers(HttpMethod.GET,"/api/v1/inventory/**").permitAll()
                        .pathMatchers(HttpMethod.GET,"/api/v1/ai/**").permitAll()

                        .pathMatchers(HttpMethod.POST, "/api/v1/products/**").hasRole("SELLER")
                        .pathMatchers(HttpMethod.PUT, "/api/v1/products/**").hasRole("SELLER")
                        .pathMatchers(HttpMethod.DELETE, "/api/v1/products/**").hasRole("SELLER")

                        .pathMatchers(HttpMethod.POST, "/api/v1/inventory/**").hasRole("SELLER")
                        .pathMatchers(HttpMethod.PUT, "/api/v1/inventory/**").hasRole("SELLER")
                        .pathMatchers(HttpMethod.DELETE, "/api/v1/inventory/**").hasRole("SELLER")

                        .pathMatchers(HttpMethod.POST, "/api/v1/categories/**").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.PUT, "/api/v1/categories/**").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.DELETE, "/api/v1/categories/**").hasRole("ADMIN")

                        .anyExchange().authenticated()
                )

                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(
                                        new KeycloakRoleConverter()
                                )
                        )
                );

        return http.build();
    }
}
