package com.janith.response;

import com.janith.model.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;

    private String message;

    private USER_ROLE role;
}
