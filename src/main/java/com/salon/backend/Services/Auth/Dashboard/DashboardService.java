package com.salon.backend.Services.Auth.Dashboard;


import com.salon.backend.DTOs.ApiResponse;
import com.salon.backend.DTOs.Auth.Dashboard.DashboardLoginRequest;
import com.salon.backend.DTOs.Auth.Dashboard.DashboardLoginResponse;
import com.salon.backend.DTOs.Salon.CreateSalonRequest;
import com.salon.backend.DTOs.Salon.CreateSalonResponse;

public interface DashboardService {
    ApiResponse<DashboardLoginResponse>  DashboardLogin(DashboardLoginRequest dashboardLoginRequest);
    ApiResponse<CreateSalonResponse> AcceptSalonRequest(CreateSalonRequest createSalonRequest, String adminEmail);
    ApiResponse<CreateSalonResponse> RejectSalonRequest(CreateSalonRequest createSalonRequest,String adminEmail, String rejectionReason);
    ApiResponse<String> blockUser(Long userId);
}
