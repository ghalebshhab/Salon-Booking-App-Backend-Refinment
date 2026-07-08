package com.salon.backend.DTOs.Salon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSalonRequest {

    private String salonName;
    private String salonDescription;
    private String salonPhoneNumber;
    private String salonEmail;
    private String salonCity;
    private Long ownerId;
    private Long maxNumberOfEmployees;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private String profilePictureUrl;
}
