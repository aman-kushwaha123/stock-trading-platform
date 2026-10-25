package com.example.services;


import com.example.entities.Holding;
import com.example.entities.Transaction;
import org.springframework.data.domain.Page;

import java.util.List;

public interface HoldingService {
    Holding createHolding(Transaction transaction);
    Holding getHoldingById(Long id);
    Page<Holding> getAllHolding(int page,int size);
    Holding updateHoldingById(Long id,Holding holding);
    Holding updateHoldingAfterBuy(Transaction transaction,Holding holding);
    Boolean deleteHoldingById(Long id);
}
