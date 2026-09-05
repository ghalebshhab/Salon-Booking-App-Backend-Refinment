package com.salon.backend.Services.Salon.Employment;

import com.salon.backend.DTOs.ApiResponse;
import com.salon.backend.DTOs.Salon.Employment.Join.*;
import com.salon.backend.DTOs.Salon.Employment.Leave.AcceptLeaveRequest;
import com.salon.backend.DTOs.Salon.Employment.Leave.CreateLeaveRequest;
import com.salon.backend.Entities.salons.employment.EmploymentRequest;
import com.salon.backend.Entities.salons.employment.LeaveRequest;

public interface EmploymentService {
ApiResponse<EmploymentRequest> joinSalon(JoinSalonRequest request, Long senderId);

ApiResponse<EmploymentRequest> sentInvitation(SentInvitation sentInvitation, Long salonId, Long senderId);

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

    ApiResponse<LeaveRequest>  createLeaveRequest(CreateLeaveRequest createLeaveRequest, Long currentUserId);

    ApiResponse<LeaveRequest> acceptLeaveRequest(
            Long requestId,
            Long currentUserId
    );

    ApiResponse<LeaveRequest>  cancelLeaveRequest(Long requestId, Long currentUserId);

    ApiResponse<EmploymentRequest> acceptInvitationAndRequestLeave(
            Long requestId,
            Long currentUserId,
            String reason
    );


}
