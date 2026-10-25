package com.example.services;

import com.example.DTOs.UserUpdateDTO;
import com.example.entities.User;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

public interface UserService {
    User createUser(User user);

    User getUserById(Long id) throws UserPrincipalNotFoundException,IllegalArgumentException;

    Page<User> getAllUser(int page, int size);

    User updateUserById(Long id, UserUpdateDTO userUpdateDTO) throws IllegalArgumentException,UserPrincipalNotFoundException;

    Boolean deleteUserById(Long id);

    void reduceCashBalance(Long userId,BigDecimal cash);






}
