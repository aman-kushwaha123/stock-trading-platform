package com.example.controllers;

import com.example.ResponseDTOs.UserResponse;
import com.example.DTOs.UserUpdateDTO;
import com.example.entities.User;
import com.example.services.UserService;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    protected UserController(UserService userService){
        this.userService=userService;
    }


    @PostMapping("/add")
    ResponseEntity<Map<String,String>> createUser(@RequestBody User user){

                userService.createUser(user);
                return ResponseEntity.ok(Map.of("message", "User Successfully Created."));


    }

    @PutMapping("/update/{id}")
    ResponseEntity<Map<String,String>> updateUser(@PathVariable(value = "id") Long id,
                                                 @RequestBody UserUpdateDTO userUpdateDTO)
    {

           User userUpdateDTO1=userService.updateUserById(id,userUpdateDTO);
           return ResponseEntity.ok(Map.of("message","Successfully updated username : "+userUpdateDTO1.getUsername()));

    }

    @GetMapping("/get/{id}")
    ResponseEntity<UserResponse> getUserById(@PathVariable(value = "id") Long id){
               UserResponse userResponse=new UserResponse();
               User result= userService.getUserById(id);
               userResponse.setUser(result);
               userResponse.setMessage(Map.of("message","Successfully"));
               return ResponseEntity.ok(userResponse);

    }

    @GetMapping("/get/all")
    ResponseEntity<Page<User>> getAllUser(@RequestParam(value = "page") int page,
                                          @RequestParam(value = "size") int size
                                          ){
         Page<User> result=userService.getAllUser(page,size);
         return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Map<String,String>> deleteUserById(@PathVariable(value="id") Long id){
        Boolean result=userService.deleteUserById(id);
        return ResponseEntity.ok(Map.of("message","Successfully user deleted."));
    }


}
