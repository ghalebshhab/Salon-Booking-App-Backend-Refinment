package com.salon.backend.Controllers.Users;


import com.salon.backend.DTOs.ApiResponse;
import com.salon.backend.Entities.users.User;
import com.salon.backend.Entities.users.UserStatus;
import com.salon.backend.Repositories.User.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:3000"})
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserRepo userRepo;


    @GetMapping
    public ApiResponse<List<User>> getUsers() {
        return ApiResponse.success("Users Fetched Successfully .",userRepo.findAll());
    }
    @GetMapping("/active")
    public ApiResponse<List<User>> getActiveUsers() {
        List<User> users=userRepo.findAll();
        List<User> activeUsers=new ArrayList<>();
        for(User user:users){
            if(user.getStatus()== UserStatus.Active){
                activeUsers.add(user);
            }
        }
        return ApiResponse.success("Active Users Fetched Successfully .",activeUsers);
    }
    @GetMapping("/blocked")
    public ApiResponse<List<User>> getBlockedUsers() {
        List<User> users=userRepo.findAll();
        List<User> blockedUsers=new ArrayList<>();
        for(User user:users){
            if(user.getStatus()== UserStatus.Blocked){
                blockedUsers.add(user);
            }
        }
        return ApiResponse.success("Block Users Fetched Successfully .",blockedUsers);
    }

}
