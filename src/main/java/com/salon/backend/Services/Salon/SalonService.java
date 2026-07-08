package com.salon.backend.Services.Salon;


import com.salon.backend.DTOs.ApiResponse;
import com.salon.backend.DTOs.Salon.CreateSalonRequest;
import com.salon.backend.DTOs.Salon.CreateSalonResponse;
import com.salon.backend.DTOs.Salon.UpdateSalonRequest;
import com.salon.backend.Entities.salons.Salon;

public interface SalonService {
    ApiResponse<CreateSalonResponse> createSalon(CreateSalonRequest createSalonRequest,Long id);
    ApiResponse<CreateSalonResponse> updateSalon(UpdateSalonRequest updateSalonRequest,Long id,String ownerEmail);
    ApiResponse<Void> deleteSalon(long id);
    ApiResponse<CreateSalonResponse> getSalonById(long id);
    ApiResponse<CreateSalonResponse> getSalonByOwnerEmail(String email);

}
