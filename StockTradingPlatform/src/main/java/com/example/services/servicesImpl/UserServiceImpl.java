package com.example.services.servicesImpl;

import com.example.DTOs.UserUpdateDTO;
import com.example.entities.User;
import com.example.repositories.User_Repo;
import com.example.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private User_Repo userRepo;

    @Override
    public User createUser(User user) throws NullPointerException {
        User result;
        if(user!=null){
            result=userRepo.save(user);
            return result;
        }
        else{
           return null;
        }


    }

    @Override
    public User getUserById(Long id)  {
        if(id==null){
            throw new IllegalArgumentException("provided id for retrieval is null");
        }
        User user=userRepo.findById(id).orElseThrow(()->new IllegalArgumentException("User not found"));
        return user;
    }

    @Override
    public Page<User> getAllUser(int page, int size) {
        // Create a pageable configuration (e.g., Page 0, 10 items per page, sorted by ID)
        Pageable pageable= PageRequest.of(page,size, Sort.by("id").ascending());

        // Returns a Page object containing the data list and metadata (totals, next page, etc.)
        return userRepo.findAll(pageable);
    }

    @Override
    public User updateUserById(Long id, UserUpdateDTO userUpdateDTO)
    {
        User result;
        if(id==null){
            throw new IllegalArgumentException("provided id is null");
        }

        if(userUpdateDTO==null){
            throw new IllegalArgumentException("provided user data for update is null");
        }

        User existUser=userRepo.findById(id).orElseThrow(()->new IllegalArgumentException("User not found"));
        if(existUser==null){
            throw new IllegalArgumentException("User not found");
        }
        existUser.setUsername(userUpdateDTO.getUsername());
        existUser.setCashBalance(userUpdateDTO.getCashBalance());
        result = userRepo.save(existUser);
        return result;

    }

    @Override
    public Boolean deleteUserById(Long id) {
        if(id!=null){
            userRepo.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public User reduceCashBalance(Long userId,BigDecimal cash){
          User user=userRepo.findById(userId).orElseThrow(()->new EntityNotFoundException("User not found"));
           if(cash==null){
               throw new IllegalArgumentException("Provided cashBalance is null.");
           }
            //• 1 if the first value is greater than the second.
            //• 0 if the values are equal.
            //• -1 if the first value is less than the second.
           if(cash.compareTo(user.getCashBalance())>0){
               throw new IllegalArgumentException("User don't have enough cashBalance.");
           }
          System.out.println("Reducing cashBalance......");
           BigDecimal newCashBalance=user.getCashBalance().subtract(cash);
           user.setCashBalance(newCashBalance);
           return userRepo.save(user);
    }

    @Override
    public User increaseCashBalance(Long userId,BigDecimal cash){

        User user=userRepo.findById(userId).orElseThrow(()->new EntityNotFoundException("User not found"));
        if(cash==null){
            throw new IllegalArgumentException("Provided cashBalance is null.");
        }
        //• 1 if the first value is greater than the second.
        //• 0 if the values are equal.
        //• -1 if the first value is less than the second.
        if(cash.compareTo(BigDecimal.valueOf(0))<0){
            throw new IllegalArgumentException("Selling price cannot be less than 0.");
        }
        System.out.println("Increasing cashBalance...");
        BigDecimal newCashBalance=user.getCashBalance().add(cash);
        user.setCashBalance(newCashBalance);
        return userRepo.save(user);
    }


}
