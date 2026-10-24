package com.example.repositories;

import com.example.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Stock_Repo extends JpaRepository<Stock,Long> {
}
