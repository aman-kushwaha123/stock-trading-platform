package com.example.DTOs;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data                // Generates Getters, Setters, toString, equals, and hashCode
@NoArgsConstructor     // Generates a default no-argument constructor (required by JPA)
@AllArgsConstructor    // Generates a constructor with all fields
@Builder          // Enables the Builder pattern for clean object creation
public class UserUpdateDTO {
    private String username;

    //Formula: integer + fraction = Total Precision
    // Enforces a maximum of 8 digits before the decimal, and exactly 2 after.
    // Total Precision = 10, Scale = 2. Max value: 99999999.99
    @Digits(integer = 15,fraction = 2,message = "Price format must be up to 8 integer digits and 2 decimals")
    private BigDecimal cashBalance;


}
