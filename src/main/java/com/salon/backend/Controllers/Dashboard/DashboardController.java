package com.salon.backend.Controllers.Dashboard;

import com.salon.backend.DTOs.ApiResponse;
import com.salon.backend.DTOs.Auth.Dashboard.DashboardLoginRequest;
import com.salon.backend.DTOs.Auth.Dashboard.DashboardLoginResponse;
import com.salon.backend.DTOs.Salon.CreateSalonRequest;
import com.salon.backend.DTOs.Salon.CreateSalonResponse;
import com.salon.backend.Entities.salons.Salon;
import com.salon.backend.Entities.salons.SalonStatus;
import com.salon.backend.Repositories.Salon.SalonRepo;
import com.salon.backend.Services.Auth.Dashboard.DashboardService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
   private final DashboardService dashboardService;
   private final SalonRepo salonRepo;
    @PostMapping("/login")
    public ApiResponse<DashboardLoginResponse> login(@RequestBody DashboardLoginRequest dashboardLoginRequest){
      return  dashboardService.DashboardLogin(dashboardLoginRequest);
    }

    @GetMapping("/salons/requested")
    public ApiResponse<List<Salon>>  requestedSalons(){
        List<Salon> requestedSalons =salonRepo.findByStatus(SalonStatus.Requested);
        return ApiResponse.success("Requested Salons Fetched Successfully",requestedSalons);
    }
    @PostMapping("/salons/requested/accept")
    public ApiResponse<CreateSalonResponse>  acceptRequestedSalons(@RequestBody CreateSalonRequest createSalonRequest, Authentication authentication){
     String adminEmail=authentication.getName();
        return dashboardService.AcceptSalonRequest(createSalonRequest,adminEmail);
    }
    @PostMapping("/salons/requested/reject")
    public ApiResponse<CreateSalonResponse>  rejectRequestedSalons(@RequestBody CreateSalonRequest createSalonRequest, Authentication authentication,@RequestBody String rejectionReason){
        String adminEmail= authentication.getName();
        return dashboardService.RejectSalonRequest(createSalonRequest,adminEmail,rejectionReason);
    }
    @PostMapping("/users/{userId}/block")
    public ApiResponse<String> blockUser(@PathVariable Long userId){
        return dashboardService.blockUser(userId);

    }

}
