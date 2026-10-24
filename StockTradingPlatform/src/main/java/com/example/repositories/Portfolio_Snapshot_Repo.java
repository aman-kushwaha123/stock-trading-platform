package com.example.repositories;

import com.example.entities.PortfolioSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Portfolio_Snapshot_Repo extends JpaRepository<PortfolioSnapshot,Long> {

}
