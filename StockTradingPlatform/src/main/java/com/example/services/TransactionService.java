package com.example.services;

import com.example.DTOs.TransactionDTO;
import com.example.entities.Transaction;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TransactionService {

    Transaction buyTrade(TransactionDTO transactionDTO);

    Transaction sellTrade(TransactionDTO transactionDTO);

    //CreatePending transaction;
    Transaction createPendingTransaction(TransactionDTO transactionDTO);

    //excecuteTrade
    void executeTrade(Transaction transaction);

    //createFailed transaction
    Transaction markASFailed(Long transactionId);

    Transaction getTransactionById(Long id);

    //Transaction cannot be updated
    //Transaction updateTransactionById(Long id);

    Page<Transaction> getAllTransactionById(int page ,int size,Long userId);

    // Transaction cannot be deleted;
    Boolean deleteTransactionById(Long id);

}
