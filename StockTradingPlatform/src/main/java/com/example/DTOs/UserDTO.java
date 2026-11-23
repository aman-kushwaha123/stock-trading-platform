package com.example.DTOs;

import com.example.Enums.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
public class UserDTO {
    private Long id;

    private String username;

    private UserRoles role= UserRoles.USER;

    private BigDecimal cashBalance=BigDecimal.ZERO;
}
