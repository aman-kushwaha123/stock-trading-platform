package com.example.services;


import com.example.entities.Holding;
import org.springframework.data.domain.Page;

import java.util.List;

public interface HoldingService {
    Holding createHolding(Holding holding);
    Holding getHoldingById(Long id);
    Page<Holding> getAllHolding(int page,int size);
    Holding updateHoldingById(Long id,Holding holding);
    Boolean deleteHoldingById(Long id);
}
