package com.example.services.servicesImpl;

import com.example.entities.Transaction;
import com.example.services.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Override
    public Transaction createTransaction(Transaction transaction) {
        return null;
    }

    @Override
    public Transaction getTransactionById(Long id) {
        return null;
    }

    @Override
    public Transaction updateTransactionById(Long id) {
        return null;
    }

    @Override
    public List<Transaction> getAllTransaction() {
        return List.of();
    }

    @Override
    public Boolean deleteTransactionById(Long id) {
        return null;
    }
}
