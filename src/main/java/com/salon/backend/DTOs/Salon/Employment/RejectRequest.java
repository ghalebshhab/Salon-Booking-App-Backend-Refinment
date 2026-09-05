package com.salon.backend.DTOs.Salon.Employment;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RejectRequest{
    public Long requestId;
    public String rejectionReason;

}