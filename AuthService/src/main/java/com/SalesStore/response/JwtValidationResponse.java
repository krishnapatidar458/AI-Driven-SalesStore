package com.SalesStore.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtValidationResponse {
    private boolean valid;
    private String email;
    private String role;
}
