package com.salon.backend.Services.Salon.Employment;

import com.salon.backend.DTOs.ApiResponse;
import com.salon.backend.DTOs.Salon.Employment.*;
import com.salon.backend.Entities.salons.employment.EmploymentRequest;

public interface EmploymentService {
ApiResponse<EmploymentRequest> joinSalon(JoinSalonRequest request, Long senderId);

ApiResponse<EmploymentRequest> sentInvitation(SentInvitation sentInvitation,Long salonId,Long senderId);

    ApiResponse<EmploymentRequest> acceptRequest(
            AcceptRequest acceptRequest,
            Long currentUserId
    );

    ApiResponse<EmploymentRequest> rejectRequest(
            RejectRequest rejectRequest,
            Long currentUserId
    );
    ApiResponse<EmploymentRequest> cancelRequest(
            CancelRequest cancelRequest,
            Long currentUserId
    );

}
