package com.example.services.servicesImpl;

import com.example.DTOs.UserCredentialsDTO;
import com.example.DTOs.UserUpdateDTO;
import com.example.ResponseDTOs.UserLoginResponseDTO;
import com.example.entities.User;
import com.example.repositories.User_Repo;
import com.example.services.UserService;
import io.jsonwebtoken.security.Password;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.Base64;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private User_Repo userRepo;

    @Autowired private JwtService jwtService;

    private AuthenticationManager authenticationManager;

    UserServiceImpl(AuthenticationManager authenticationManager){
        this.authenticationManager=authenticationManager;
    }




    @Override
    public User createUser(User user) throws NullPointerException {
        User result;
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        if(user!=null){
            String hashedPass=passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPass);
            result=userRepo.save(user);
            return result;
        }
        else{
           return null;
        }


    }

    @Override
    public UserLoginResponseDTO verifyUser(UserCredentialsDTO userCredentialsDTO) throws AuthenticationCredentialsNotFoundException {
        String token="";

        User user=userRepo.findByUsername(userCredentialsDTO.getUsername())
                .orElseThrow(()->new IllegalArgumentException("User not found."));
        Authentication authentication=authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userCredentialsDTO.getUsername(),userCredentialsDTO.getPassword()));
        if(authentication.isAuthenticated()){
            token=jwtService.generateToken(userCredentialsDTO.getUsername());
            UserLoginResponseDTO userLoginResponseDTO=UserLoginResponseDTO.builder()
                    .user(user)
                    .token(token)
                    .build();
            return  userLoginResponseDTO;
        }
        else{
            throw new AuthenticationCredentialsNotFoundException("Given Credentials are not found.");
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
