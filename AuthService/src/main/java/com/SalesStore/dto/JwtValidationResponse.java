package com.SalesStore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtValidationResponse {

    private boolean vaild;
    private String email;
    private String role;

}
