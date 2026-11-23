package com.example.controllers;

import com.example.DTOs.UserCredentialsDTO;
import com.example.DTOs.UserDTO;
import com.example.DTOs.UserRegisterDTO;
import com.example.Enums.UserRoles;
import com.example.ResponseDTOs.UserLoginResponseDTO;
import com.example.ResponseDTOs.UserResponse;
import com.example.DTOs.UserUpdateDTO;
import com.example.entities.User;
import com.example.repositories.User_Repo;
import com.example.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200",allowCredentials = "true")
@Tag(
        name="User and auth related apis.",
        description = "There are several endpoints such as /user/register ,/user/login for authenticaitons."
)
public class UserController {

    private final UserService userService;

    @Autowired private User_Repo userRepo;

    protected UserController(UserService userService){
        this.userService=userService;
    }


    @PostMapping("/register")
    @Operation()
    ResponseEntity<Map<String,String>> register(@RequestBody UserRegisterDTO userRegisterDTO){
                userService.createUser(userRegisterDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "User Successfully Created."));
    }

    @PostMapping("/login")
    ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserCredentialsDTO userCredentialsDTO){
        System.out.println(userCredentialsDTO.getPassword());
        UserLoginResponseDTO userLoginResponseDTO=userService.verifyUser(userCredentialsDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userLoginResponseDTO);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<UserResponse> updateUser(@PathVariable(value = "id") Long id,
                                                 @RequestBody UserUpdateDTO userUpdateDTO)
    {

           User userUpdateDTO1=userService.updateUserById(id,userUpdateDTO);
            UserDTO userDTO=new UserDTO();
           BeanUtils.copyProperties(userUpdateDTO1,userDTO);
           UserResponse userResponse=new UserResponse();
           userResponse.setUser(userDTO);
           userResponse.setMessage(Map.of("message","Successfully updated."));
           return ResponseEntity.ok(userResponse);

    }

    @PatchMapping("/update/roleUpdate/{userId}")
    ResponseEntity<UserResponse> updateUser(@PathVariable Long userId)
    {
        User user=userRepo.findById(userId).orElseThrow(()->new IllegalArgumentException("User not found."));
        if(user.getRole().equals(UserRoles.USER)){
            user.setRole(UserRoles.ADMIN);
        }
        else{
            user.setRole(UserRoles.USER);
        }
        User userUpdateDTO1=userRepo.save(user);
        UserDTO userDTO=new UserDTO();
        BeanUtils.copyProperties(userUpdateDTO1,userDTO);
        UserResponse userResponse=new UserResponse();
        userResponse.setUser(userDTO);
        userResponse.setMessage(Map.of("message","Successfully role updated."));
        return ResponseEntity.ok(userResponse);

    }

    @GetMapping("/get/{id}")
    ResponseEntity<UserResponse> getUserById(@PathVariable(value = "id") Long id){
               UserResponse userResponse=new UserResponse();
               User result= userService.getUserById(id);
               UserDTO userDTO=new UserDTO();
               BeanUtils.copyProperties(result,userDTO);
               userResponse.setUser(userDTO);
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
