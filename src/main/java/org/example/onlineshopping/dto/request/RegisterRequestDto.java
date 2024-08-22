package org.example.onlineshopping.dto.request;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String username;
    private String email;
    private String password;
}
