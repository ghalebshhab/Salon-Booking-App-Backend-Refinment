package com.salon.backend.Controllers.Salons;


import com.salon.backend.DTOs.ApiResponse;
import com.salon.backend.DTOs.Salon.Employment.Join.*;
import com.salon.backend.DTOs.Salon.Employment.Leave.CreateLeaveRequest;
import com.salon.backend.Entities.salons.employment.EmploymentRequest;
import com.salon.backend.Entities.salons.employment.LeaveRequest;
import com.salon.backend.Services.Salon.Employment.EmploymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employment")
@RequiredArgsConstructor
public class EmploymentController {

    private final EmploymentService employmentService;




    @PostMapping("/join")
    public ResponseEntity<ApiResponse<EmploymentRequest>> joinSalon(
            @RequestBody JoinSalonRequest request,
            @RequestParam Long currentUserId) {

        return ResponseEntity.ok(
                employmentService.joinSalon(
                        request,
                        currentUserId
                )
        );
    }




    @PostMapping("/invite/{salonId}")
    public ResponseEntity<ApiResponse<EmploymentRequest>> sendInvitation(
            @RequestBody SentInvitation request,
            @PathVariable Long salonId,
            @RequestParam Long currentUserId) {

        return ResponseEntity.ok(
                employmentService.sentInvitation(
                        request,
                        salonId,
                        currentUserId
                )
        );
    }




    @PostMapping("/accept")
    public ResponseEntity<ApiResponse<EmploymentRequest>> acceptRequest(
            @RequestBody AcceptRequest request,
            @RequestParam Long currentUserId) {

        return ResponseEntity.ok(
                employmentService.acceptRequest(
                        request,
                        currentUserId
                )
        );
    }



    @PostMapping("/reject")
    public ResponseEntity<ApiResponse<EmploymentRequest>> rejectRequest(
            @RequestBody RejectRequest request,
            @RequestParam Long currentUserId) {

        return ResponseEntity.ok(
                employmentService.rejectRequest(
                        request,
                        currentUserId
                )
        );
    }




    @PostMapping("/cancel")
    public ResponseEntity<ApiResponse<EmploymentRequest>> cancelRequest(
            @RequestBody CancelRequest request,
            @RequestParam Long currentUserId) {

        return ResponseEntity.ok(
                employmentService.cancelRequest(
                        request,
                        currentUserId
                )
        );
    }



    @PostMapping("/leave/request")
    public ResponseEntity<ApiResponse<LeaveRequest>> requestLeaveSalon(
            @RequestBody CreateLeaveRequest request,
            @RequestParam Long currentUserId) {

        return ResponseEntity.ok(
                employmentService.createLeaveRequest(
                        request,
                        currentUserId
                )
        );
    }




    @PostMapping("/leave/accept/{requestId}")
    public ResponseEntity<ApiResponse<LeaveRequest>> acceptLeaveRequest(
            @PathVariable Long requestId,
            @RequestParam Long currentUserId) {

        return ResponseEntity.ok(
                employmentService.acceptLeaveRequest(
                        requestId,
                        currentUserId
                )
        );
    }




    @PostMapping("/leave/cancel/{requestId}")
    public ResponseEntity<ApiResponse<LeaveRequest>> cancelLeaveRequest(
            @PathVariable Long requestId,
            @RequestParam Long currentUserId) {

        return ResponseEntity.ok(
                employmentService.cancelLeaveRequest(
                        requestId,
                        currentUserId
                )
        );
    }




    @PostMapping("/invitation/accept-and-leave")
    public ResponseEntity<ApiResponse<EmploymentRequest>>
    acceptInvitationAndRequestLeave(
            @RequestBody AcceptRequest request,
            @RequestParam Long currentUserId,
            @RequestParam(required = false) String reason) {

        return ResponseEntity.ok(
                employmentService.acceptInvitationAndRequestLeave(
                        request.requestId(),
                        currentUserId,
                        reason
                )
        );
    }
}