package com.salon.backend.Controllers.Salons;


import com.salon.backend.DTOs.ApiResponse;
import com.salon.backend.DTOs.Salon.CreateSalonRequest;
import com.salon.backend.DTOs.Salon.CreateSalonResponse;
import com.salon.backend.DTOs.Salon.UpdateSalonRequest;
import com.salon.backend.Entities.salons.Salon;
import com.salon.backend.Entities.salons.SalonStatus;
import com.salon.backend.Repositories.Salon.SalonRepo;
import com.salon.backend.Repositories.User.UserRepo;
import com.salon.backend.Services.Salon.SalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/salons")
@RequiredArgsConstructor
public class SalonController {
    private final SalonRepo salonRepo;
    private final SalonService salonService;
    private final UserRepo  userRepo;
    @GetMapping
    public ApiResponse<List<Salon>> getAllSalons() {
        return ApiResponse.success("Salons Fetched Successfully",salonRepo.findAll());
    }
    @GetMapping("/active")
    public ApiResponse<List<Salon>> getActiveSalons() {
        List<Salon> salons = salonRepo.findAll();
        List<Salon> activeSalons=new ArrayList<>();
        for (Salon salon:salons) {
            if(salon.getStatus()== SalonStatus.Accepted) {
                activeSalons.add(salon);
            }
        }
        return ApiResponse.success("Active Salons Fetched Successfully",activeSalons);

    }
    @PostMapping("/create")
    public ApiResponse<CreateSalonResponse> createSalon(@RequestBody CreateSalonRequest createSalonRequest, Authentication authentication) {
        Long userId =userRepo.findByemail(authentication.getName()).getId();
        return salonService.createSalon(createSalonRequest,userId);
    }
    @PutMapping("/{salonId}/edit")
    public ApiResponse<CreateSalonResponse> updateSalon(@RequestBody UpdateSalonRequest updateSalonRequest,@PathVariable Long salonId,Authentication authentication) {
        return salonService.updateSalon(updateSalonRequest,salonId,authentication.getName());
    }




}
