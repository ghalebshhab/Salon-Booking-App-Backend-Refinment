package com.salon.backend.Services.Salon.Employment;

import com.salon.backend.DTOs.ApiResponse;
import com.salon.backend.DTOs.Salon.Employment.*;
import com.salon.backend.Entities.salons.employment.EmploymentRequest;

public interface EmploymentService {
ApiResponse<EmploymentRequest> joinSalon(JoinSalonRequest request, Long senderId);
ApiResponse<EmploymentRequest> acceptRequest(AcceptRequest acceptRequest);
ApiResponse<EmploymentRequest> sentInvitation(SentInvitation sentInvitation,Long salonId);
ApiResponse<EmployeeRequestResponse> rejectRequest(RejectRequest rejectRequest);

}
