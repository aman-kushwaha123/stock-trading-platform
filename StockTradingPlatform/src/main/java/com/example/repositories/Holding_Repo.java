package com.example.repositories;

import com.example.entities.Holding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Holding_Repo extends JpaRepository<Holding,Long> {

}
