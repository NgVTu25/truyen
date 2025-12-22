package com.search.truyen.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class userDTO {

    private String username;

    private String password;

    private String email;

    private String role;
}
