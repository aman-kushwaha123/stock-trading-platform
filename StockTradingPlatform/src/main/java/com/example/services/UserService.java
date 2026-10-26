package com.example.services;

import com.example.DTOs.UserUpdateDTO;
import com.example.entities.User;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

public interface UserService {
    User createUser(User user);

    User getUserById(Long id) ;

    Page<User> getAllUser(int page, int size);

    User updateUserById(Long id, UserUpdateDTO userUpdateDTO) ;


    Boolean deleteUserById(Long id);

    User reduceCashBalance(Long userId,BigDecimal cash);

    User increaseCashBalance(Long userId,BigDecimal cash);






}
