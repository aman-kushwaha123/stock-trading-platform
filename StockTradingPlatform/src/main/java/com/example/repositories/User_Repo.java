package com.example.repositories;

import com.example.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface User_Repo extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndPassword(String Username,String password);
}
