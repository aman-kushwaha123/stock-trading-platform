package com.example.services.servicesImpl;

import com.example.entities.User;
import com.example.entities.UserPrincipal;
import com.example.repositories.User_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired User_Repo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user=userRepo.findByUsername(username);
        if(user.isPresent()){
               return new UserPrincipal(user.get());
        }
        throw new UsernameNotFoundException("User not found by this username : "+username);
    }
}
