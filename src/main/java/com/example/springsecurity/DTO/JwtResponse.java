package com.example.springsecurity.DTO;

import lombok.Data;

@Data
public class JwtResponse {
    private Long id;
    private String jwt;

    public JwtResponse(Long id, String jwt) {
        this.id = id;
        this.jwt = jwt;
    }
}
