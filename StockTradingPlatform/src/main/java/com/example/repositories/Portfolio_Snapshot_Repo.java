package com.example.repositories;

import com.example.entities.PortfolioSnapshot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Portfolio_Snapshot_Repo extends JpaRepository<PortfolioSnapshot,Long> {
    Page<PortfolioSnapshot> findAllByUserId(Long userId, Pageable pageable);

}
