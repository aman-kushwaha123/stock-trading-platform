package com.example.repositories;

import com.example.entities.Holding;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Holding_Repo extends JpaRepository<Holding,Long> {

    Optional<Holding> findByUserIdAndStockId(Long userId, Long stockId);
    Page<Holding> findAllByUserId(Long userId, Pageable pageable);

}
