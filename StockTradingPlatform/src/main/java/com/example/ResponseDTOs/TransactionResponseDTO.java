package com.example.ResponseDTOs;

import com.example.entities.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDTO {
    private Transaction transaction=null;
    private Page<Transaction>  transactionsByUserId=null;
    private String message;
}
