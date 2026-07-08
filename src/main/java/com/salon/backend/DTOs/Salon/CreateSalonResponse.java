package com.salon.backend.DTOs.Salon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSalonResponse {

    private Long id;
    private String salonName;
    private String salonPhoneNumber;
    private String salonEmail;
    private String salonAddress;
    private String ownerPhoneNumber;
    private String ownerName;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private String profilePictureUrl;
    private Long currentNumOfEmployees;
    private Long maxNumOfEmployees;
}
