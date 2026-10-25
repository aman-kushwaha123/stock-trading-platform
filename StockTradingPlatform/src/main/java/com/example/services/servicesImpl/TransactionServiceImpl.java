package com.example.services.servicesImpl;

import com.example.Enums.TransactionType;
import com.example.entities.Holding;
import com.example.entities.Stock;
import com.example.entities.Transaction;
import com.example.entities.User;
import com.example.repositories.Stock_Repo;
import com.example.repositories.Transaction_Repo;
import com.example.repositories.User_Repo;
import com.example.services.TransactionService;
import com.example.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private User_Repo userRepo;
    @Autowired
    private Stock_Repo stockRepo;
    @Autowired
    private HoldingServiceImpl holdingServiceImpl;
    @Autowired
    private Transaction_Repo transactionRepo;

    @Override
    @Transactional(rollbackFor = Exception.class)      //for all exceptions
    public Transaction buyTrade(Transaction transaction) {
        User user=userRepo.findById(transaction.getUser().getId())
                .orElseThrow(()->new EntityNotFoundException("User not exist"));
        Stock stock=stockRepo.findById(transaction.getStock().getId())
                .orElseThrow(()->new EntityNotFoundException("Stock not exist"));

        BigDecimal totalPrice=transaction.getPricePerShare()
                .multiply(BigDecimal.valueOf(transaction.getQuantity()));

        transaction.setTotalPrice(totalPrice);
        transaction.setTransactionType(TransactionType.BUY);

        //reducing users cashBalance
        userServiceImpl.reduceCashBalance(user.getId(),transaction.getTotalPrice());

        //updating Holdings
        holdingServiceImpl.createHolding(transaction);


        Transaction transaction1=transactionRepo.save(transaction);
        return transaction1;
    }

    @Override
    public Transaction sellTrade(Transaction transaction) {
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
