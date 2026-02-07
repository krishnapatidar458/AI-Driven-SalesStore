package com.salesstore.api_gateway.config;

import jakarta.annotation.Nonnull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakRoleConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    private final JwtAuthenticationConverter delegate = new JwtAuthenticationConverter();

    public KeycloakRoleConverter(){
        delegate.setJwtGrantedAuthoritiesConverter(jwt -> {
            Map<String,Object> realmAccess = (Map<String,Object>) jwt.getClaims().get("realm_access");

            if(realmAccess == null || realmAccess.isEmpty()){
                return new ArrayList<>();
            }

            Collection<GrantedAuthority> authorities = ((List<String>) realmAccess.get("roles"))
                    .stream()
                    .map(roleName -> "ROLE_" + roleName)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            return authorities;
        });
    }

    @Override
    public Mono<AbstractAuthenticationToken> convert(@Nonnull Jwt jwt) {
        return new ReactiveJwtAuthenticationConverterAdapter(delegate).convert(jwt);
    }
}
