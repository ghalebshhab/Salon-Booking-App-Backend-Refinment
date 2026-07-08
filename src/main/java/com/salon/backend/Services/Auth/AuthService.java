package com.salon.backend.Services.Auth;


import com.salon.backend.DTOs.ApiResponse;
import com.salon.backend.DTOs.Auth.Login.LoginRequest;
import com.salon.backend.DTOs.Auth.Login.LoginResponse;
import com.salon.backend.DTOs.Auth.Rigester.RigesterRequest;
import com.salon.backend.DTOs.Auth.Rigester.RigesterResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ApiResponse<RigesterResponse> Rigester(RigesterRequest request);
    ApiResponse<LoginResponse> Login(LoginRequest request);
    ApiResponse<ResponseEntity> Logout();
}
