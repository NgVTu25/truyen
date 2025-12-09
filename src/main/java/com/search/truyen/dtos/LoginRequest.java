package com.search.truyen.dtos;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}