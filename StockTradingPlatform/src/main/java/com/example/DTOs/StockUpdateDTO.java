package com.example.DTOs;

import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockUpdateDTO {

    private String symbol;
    private String companyName;
    @Digits(integer = 15,fraction = 2,message="price should be between 0 to 15 digits")
    private BigDecimal price;

}
