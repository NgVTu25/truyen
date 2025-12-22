package com.search.truyen.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class userDTO {

    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String role;
}
