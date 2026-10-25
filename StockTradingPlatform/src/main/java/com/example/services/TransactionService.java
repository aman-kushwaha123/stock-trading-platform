package com.example.services;

import com.example.entities.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction buyTrade(Transaction transaction);

    Transaction sellTrade(Transaction transaction);

    Transaction getTransactionById(Long id);

    Transaction updateTransactionById(Long id);

    List<Transaction> getAllTransaction();

    Boolean deleteTransactionById(Long id);

}
