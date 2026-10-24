package com.example.services.servicesImpl;

import com.example.entities.PortfolioSnapshot;
import com.example.services.PortfolioSnapshotService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioServiceImpl implements PortfolioSnapshotService {
    @Override
    public PortfolioSnapshot createPortfolioSnapshot(PortfolioSnapshot portfolioSnapshot) {
        return null;
    }

    @Override
    public PortfolioSnapshot getPortfolioSnapshotById(Long id) {
        return null;
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
