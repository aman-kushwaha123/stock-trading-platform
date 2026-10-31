package com.example.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCredentialsDTO {
    @NotNull(message = "username should not be null.")
    private String username;
    @NotNull(message = "password should not be null.")
    private String password;
}
