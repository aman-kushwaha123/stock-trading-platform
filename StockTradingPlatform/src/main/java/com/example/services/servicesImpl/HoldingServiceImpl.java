package com.example.services.servicesImpl;

import com.example.entities.Holding;
import com.example.entities.Stock;
import com.example.entities.Transaction;
import com.example.entities.User;
import com.example.repositories.Holding_Repo;
import com.example.services.HoldingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class HoldingServiceImpl implements HoldingService {
    @Autowired
    private Holding_Repo holdingRepo;

    @Override
    public Holding createHolding(Transaction transaction) {
        if(transaction==null){
            throw new IllegalArgumentException("Provided transaction is null");
        }
        Optional<Holding> holding=holdingRepo.findByUserIdAndStockId(transaction.getUser().getId(),
                        transaction.getStock().getId());
        if(holding.isPresent()){
            Holding result=updateHoldingAfterBuy(transaction,holding.get());
            return  result;
        }
        Holding holding1=new Holding();
        BigDecimal totalInvestment=transaction.getTotalPrice();
        BigDecimal avgPrice=totalInvestment
                .divide(BigDecimal.valueOf(transaction.getQuantity()), 2, RoundingMode.HALF_UP);
        holding1.setTotalInvestment(totalInvestment);
        holding1.setAverageBuyPrice(avgPrice);
        holding1.setStock(transaction.getStock());
        holding1.setUser(transaction.getUser());
        holding1.setQuantity(transaction.getQuantity());
        Holding result=holdingRepo.save(holding1);
        return result;
    }

    @Override
    public Holding getHoldingById(Long id) {
        Holding holding=holdingRepo.findById(id).orElseThrow(()->new EntityNotFoundException("Holding not found"));
        return holding;
    }

    @Override
    public Page<Holding> getAllHolding(int page,int size) {
        Pageable pageable= PageRequest.of(page,size, Sort.by("id").ascending());
        Page<Holding> Holdings=holdingRepo.findAll(pageable);
        return Holdings;
    }

    @Override
    public Holding updateHoldingById(Long id,Holding holding) {
        Holding holding1=holdingRepo.findById(id).orElseThrow(()->new EntityNotFoundException("Holding not found"));
        holding1.setUser(holding.getUser());
        holding1.setQuantity(holding.getQuantity());
        holding1.setStock(holding.getStock());
        holding1.setTotalInvestment(holding.getTotalInvestment());
        holding1.setAverageBuyPrice(holding.getAverageBuyPrice());
        return holding1;
    }


    @Override
    public Holding updateHoldingAfterBuy(Transaction transaction,Holding holding){
        if(transaction==null){
            throw new IllegalArgumentException("Provided transaction is null");
        }
        User txnUser=transaction.getUser();
        Stock txnStock=transaction.getStock();

        Integer oldQuantity=holding.getQuantity();
        BigDecimal oldTotalInvestment=holding.getTotalInvestment();
        BigDecimal oldAvgBuyPrice=holding.getAverageBuyPrice();

        Integer newQuantity=oldQuantity+transaction.getQuantity();
        BigDecimal newTotalInvestment=oldTotalInvestment.add(transaction.getTotalPrice());
        BigDecimal newAvgBuyPrice=newTotalInvestment
                .divide(BigDecimal.valueOf(newQuantity), 4, RoundingMode.HALF_UP);

        holding.setQuantity(newQuantity);
        holding.setTotalInvestment(newTotalInvestment);
        holding.setAverageBuyPrice(newAvgBuyPrice);

        Holding result=holdingRepo.save(holding);
        return  holding;
    }




    @Override
    public Boolean deleteHoldingById(Long id) {
        if(id!=null){
            holdingRepo.deleteById(id);
            return true;
        }
        else{
            return false;
        }

    }
}
