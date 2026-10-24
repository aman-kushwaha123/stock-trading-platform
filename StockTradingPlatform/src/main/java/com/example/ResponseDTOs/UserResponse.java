package com.example.ResponseDTOs;

import com.example.entities.User;
import lombok.Data;

import java.util.Map;

@Data
public class UserResponse {
    private User user;
    private Map<String,String> message;
}
