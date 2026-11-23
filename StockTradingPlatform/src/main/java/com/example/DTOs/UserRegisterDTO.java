package com.example.DTOs;


import com.example.Enums.UserRoles;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterDTO {
    @NotBlank(message="Username should not be null.")
    private String username;

    @NotEmpty(message="Password should not be null.")
    private String password;

    @NotEmpty(message="CashBalance should not not null and its length or size must be greater than zero.")
    private BigDecimal cashBalance=BigDecimal.ZERO;
}
