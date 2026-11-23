package com.example.ResponseDTOs;

import com.example.DTOs.UserDTO;
import com.example.entities.User;
import lombok.Data;

import java.util.Map;

@Data
public class UserResponse {
    private UserDTO user;
    private Map<String,String> message;
}
