package com.example.services.servicesImpl;

import com.example.DTOs.TransactionDTO;
import com.example.Enums.TransactionStatus;
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
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired private UserServiceImpl userServiceImpl;
    @Autowired private User_Repo userRepo;
    @Autowired private Stock_Repo stockRepo;
    @Autowired private HoldingServiceImpl holdingServiceImpl;
    @Autowired private Transaction_Repo transactionRepo;
    @Lazy         //This tells Spring to inject a lightweight proxy container instead of the actual bean on startup, breaking the circular loop.
    @Autowired  private TransactionServiceImpl self;


    @Override
    //for all exceptions
    public Transaction buyTrade(TransactionDTO transactionDTO) {
            //separate Transaction
            Transaction transaction=self.createPendingTransaction(transactionDTO);
            System.out.println("Creating Pending Transaction.... and saved.");
        try{
            //if no exception then reduce user cashBalance and update Holding;
            //separate Transaction
             self.executeTrade(transaction);
             System.out.println("Executing trade type : "+transaction.getTransactionType());

             transaction.setStatus(TransactionStatus.COMPLETED);
             Transaction result=transactionRepo.save(transaction);
             System.out.println("Transaction completed and saved.");
             return result;

        }
        catch(Exception e){
             //separate Transaction
              Transaction failedTransaction = self.markASFailed(transaction.getId());
              throw e;



        }

    }

    @Override
    public Transaction sellTrade(TransactionDTO transactionDTO) {
        //separate Transaction
        Transaction transaction=self.createPendingTransaction(transactionDTO);
        System.out.println("Creating Pending Transaction.... and saved.");
        try{
            //if no exception then reduce user cashBalance and update Holding;
            //separate Transaction
            self.executeTrade(transaction);
            System.out.println("Executing trade type : "+transaction.getTransactionType());

            transaction.setStatus(TransactionStatus.COMPLETED);
            Transaction result=transactionRepo.save(transaction);
            return result;

        }
        catch(Exception e){
            //separate Transaction
            System.out.println("Transaction failed and saved.. : "+e);
            return self.markASFailed(transaction.getId());


        }
    }



    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)           //Independent Rollback: The inner method runs in a completely separate physical transaction.
    public Transaction createPendingTransaction(TransactionDTO transactionDTO) {
        User user=userRepo.findById(transactionDTO.getUser().getId())
                .orElseThrow(()->new EntityNotFoundException("User not exist"));

        Stock stock=stockRepo.findById(transactionDTO.getStock().getId())
                .orElseThrow(()->new EntityNotFoundException("Stock not exist"));

        BigDecimal totalPrice=transactionDTO.getPricePerShare()
                .multiply(BigDecimal.valueOf(transactionDTO.getQuantity()));

        Transaction transaction=new Transaction();
        transaction.setUser(user);
        transaction.setStock(stock);
        transaction.setTotalPrice(totalPrice);
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setQuantity(transactionDTO.getQuantity());
        transaction.setPricePerShare(transactionDTO.getPricePerShare());

        transaction.setStatus(TransactionStatus.PENDING);
        //If an exception happens BEFORE transactionRepo.save(transaction):
        // (For example, User not exist or Stock not exist is thrown).
        // The method stops immediately. No transaction is saved to the database.
        Transaction result=transactionRepo.save(transaction);
        return result;

    }

    @Override
    @Transactional(rollbackFor = Exception.class,propagation =Propagation.REQUIRES_NEW)
    public void executeTrade(Transaction transaction) {
        if(transaction==null){
            throw new IllegalArgumentException("Provided transaction is null");
        }

        //reducing users cashBalance
        if(transaction.getTransactionType()==TransactionType.BUY) {
            System.out.println("reducing User Balance....");
            User reducedUser=userServiceImpl.reduceCashBalance(transaction.getUser().getId(), transaction.getTotalPrice());
            transaction.setUser(reducedUser);
        }

        //increaseCashBalance
        if(transaction.getTransactionType()==TransactionType.SELL) {
            System.out.println("increasing User Balance....");
            User increasedUser=userServiceImpl.increaseCashBalance(transaction.getUser().getId(), transaction.getTotalPrice());
            transaction.setUser(increasedUser);
        }

        System.out.println("Creating or updating Holding");
        //updating Holdings
        holdingServiceImpl.createHolding(transaction);
    }

    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public Transaction markASFailed(Long  transactionId) {
        Transaction transaction1=transactionRepo.findById(transactionId)
                .orElseThrow(()->new EntityNotFoundException("Transaction not found."));
        transaction1.setStatus(TransactionStatus.FAILED);
        return transactionRepo.save(transaction1);
    }


    @Override
    public Transaction getTransactionById(Long id) {
        Transaction transaction=transactionRepo.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Transaction not found"));
        return transaction;
    }


    @Override
    public Page<Transaction> getAllTransactionById(int page,int size,Long userId) {
        if(page<0 && size<0){
            throw new IllegalArgumentException("Page and size should be greater than 0.");
        }
        PageRequest pageable= PageRequest.of(page,size);
        Page<Transaction> pageResult=transactionRepo.findAllByUserId(userId,pageable);
        return pageResult;
    }

    @Override
    public Boolean deleteTransactionById(Long id) {
        if(id!=null){
            transactionRepo.deleteById(id);
            return true;
        }
        else{
            return false;
        }

    }

    public TransactionServiceImpl() {
        super();
    }
}
