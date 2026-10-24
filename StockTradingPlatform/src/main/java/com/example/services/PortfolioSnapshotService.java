package com.example.services;

import com.example.entities.PortfolioSnapshot;

import java.util.List;

public interface PortfolioSnapshotService {

    PortfolioSnapshot createPortfolioSnapshot(PortfolioSnapshot portfolioSnapshot);
    PortfolioSnapshot getPortfolioSnapshotById(Long id);
    List<PortfolioSnapshot> portfolioSnapshots();
    PortfolioSnapshot updatePortfolioById(Long id);
    Boolean deleteSnapshotById(Long id);
}
