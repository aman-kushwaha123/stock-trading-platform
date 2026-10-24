package com.example.services.servicesImpl;

import com.example.entities.Holding;
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

import java.util.List;

@Service
public class HoldingServiceImpl implements HoldingService {
    @Autowired
    private Holding_Repo holdingRepo;

    @Override
    public Holding createHolding(Holding holding) {
        if(holding==null){
            throw new NullPointerException("Provided holding is null");
        }
        Holding result=holdingRepo.save(holding);
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
        holding1.setAverageBuyPrice(holding.getAverageBuyPrice());
        holding1.setStock(holding.getStock());
        return holding1;
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
