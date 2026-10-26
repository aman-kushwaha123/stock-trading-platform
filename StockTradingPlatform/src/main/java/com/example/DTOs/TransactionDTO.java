package com.example.DTOs;

import com.example.Enums.TransactionType;
import com.example.entities.Stock;
import com.example.entities.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {
    @NotNull(message="User detail is required.")
    private User user;

    @NotNull(message="Stock detail is required.")
    private Stock stock;

    @NotNull(message="Quantity is required.")
    private Integer quantity;

    @NotNull(message="Price per share is required.")
    private BigDecimal pricePerShare;

    private TransactionType transactionType;

}
