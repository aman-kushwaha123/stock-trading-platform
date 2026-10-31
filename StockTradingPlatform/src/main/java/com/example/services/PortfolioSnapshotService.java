package com.example.services;

import com.example.entities.PortfolioSnapshot;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PortfolioSnapshotService {

    void createPortfolioSnapshot(Long userId);
    Page<PortfolioSnapshot> getPortfolioSnapshotByUserId(int page,int size,Long userId);
    List<PortfolioSnapshot> portfolioSnapshots();
    PortfolioSnapshot updatePortfolioById(Long id);
    Boolean deleteSnapshotById(Long id);
}
