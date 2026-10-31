package com.example.services.servicesImpl;

import com.example.entities.Holding;
import com.example.entities.PortfolioSnapshot;
import com.example.entities.User;
import com.example.repositories.Holding_Repo;
import com.example.repositories.Portfolio_Snapshot_Repo;
import com.example.repositories.User_Repo;
import com.example.services.PortfolioSnapshotService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PortfolioServiceImpl implements PortfolioSnapshotService {
    @Autowired
    private Holding_Repo holdingRepo;

    @Autowired private Portfolio_Snapshot_Repo portfolioSnapshotRepo;

    @Autowired
    private User_Repo userRepo;
    @Override
    public void createPortfolioSnapshot(Long userId) {
        User user=userRepo.findById(userId)
                .orElseThrow(()->new EntityNotFoundException("User not found"));
        List<Holding> holdings=holdingRepo.findByUserId(userId);
        BigDecimal stockMarketValue=BigDecimal.ZERO;
        BigDecimal  totalPortfolioValue=BigDecimal.ZERO;
        BigDecimal totalInvestment=BigDecimal.ZERO;
        if(!holdings.isEmpty()){
            for(Holding holding:holdings){
                   BigDecimal quantity=BigDecimal.valueOf(holding.getQuantity());
                    stockMarketValue=stockMarketValue.add(holding.getStock().getPrice()
                            .multiply(quantity));
                     totalInvestment=holding.getAverageBuyPrice().multiply(quantity);
            }
            totalPortfolioValue=totalInvestment.add(user.getCashBalance());
            PortfolioSnapshot portfolioSnapshot=new PortfolioSnapshot();
            portfolioSnapshot.setUser(user);
            portfolioSnapshot.setCashBalance(user.getCashBalance());
            portfolioSnapshot.setStockValue(stockMarketValue);
            portfolioSnapshot.setTotalPortFolioValue(totalPortfolioValue);
            portfolioSnapshotRepo.save(portfolioSnapshot);
        }
        else{
            throw new IllegalArgumentException("Holdings not found for this username : "+user.getUsername());
        }

    }

    @Override
    public Page<PortfolioSnapshot> getPortfolioSnapshotByUserId(int page,int size,Long userId) {
        Pageable pageable= PageRequest.of(page,size);
        Page<PortfolioSnapshot> portfolioSnapshots=portfolioSnapshotRepo.findAllByUserId(userId,pageable);
        return portfolioSnapshots;
    }

    @Override
    public List<PortfolioSnapshot> portfolioSnapshots() {
        return List.of();
    }

    @Override
    public PortfolioSnapshot updatePortfolioById(Long id) {
        return null;
    }

    @Override
    public Boolean deleteSnapshotById(Long id) {
        return null;
    }
}
