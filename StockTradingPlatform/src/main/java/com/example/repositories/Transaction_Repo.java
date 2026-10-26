package com.example.repositories;

import com.example.entities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Transaction_Repo extends JpaRepository<Transaction,Long> {

    Page<Transaction> findAllByUserId(Long userId,Pageable pageable);
}
