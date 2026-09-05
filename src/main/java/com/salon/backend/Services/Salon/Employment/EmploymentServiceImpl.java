package com.salon.backend.Services.Salon.Employment;

import com.salon.backend.DTOs.ApiResponse;
import com.salon.backend.DTOs.Salon.Employment.*;
import com.salon.backend.Entities.salons.Salon;
import com.salon.backend.Entities.salons.SalonStatus;
import com.salon.backend.Entities.salons.employment.EmploymentRequest;
import com.salon.backend.Entities.salons.employment.EmploymentRequestStatus;
import com.salon.backend.Entities.salons.employment.RequestType;
import com.salon.backend.Entities.users.User;
import com.salon.backend.Entities.users.UserRole;
import com.salon.backend.Repositories.Salon.EmploymentRepo;
import com.salon.backend.Repositories.Salon.SalonRepo;
import com.salon.backend.Repositories.User.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class EmploymentServiceImpl implements EmploymentService {
    private final SalonRepo salonRepo;
    private final UserRepo userRepo;
    private final EmploymentRepo employmentRepo;
    @Override
    public ApiResponse<EmploymentRequest> joinSalon(JoinSalonRequest request,Long senderId) {
        Optional<Salon> optionalsalon=salonRepo.findById(request.getSalonId());
        Optional<User> optionalUser=userRepo.findById(senderId);

        if(optionalsalon.isEmpty()){
            return ApiResponse.error("Salon not found");
        }
        if(optionalUser.isEmpty()){
            return ApiResponse.error("Sender not found");
        }
        User user=optionalUser.get();
        Salon salon=optionalsalon.get();


        if(user.getRole()!=UserRole.USER){
            return ApiResponse.error("Your role dont allow you to join the salon as employee");
        }
        if(salon.getStatus() != SalonStatus.Accepted){
            return ApiResponse.error("Salon is not active , you can not join ");
        }
        if(salon.getCurrentEmployeesNumber()>= salon.getMaxEmployeesNumber()){
            return ApiResponse.error("Salon is already full");
        }

        Optional<EmploymentRequest> existingRequest =
                employmentRepo.findPendingRequest(
                        salon,
                        user,
                        EmploymentRequestStatus.Requested
                );

        if (existingRequest.isPresent()) {

            EmploymentRequest emprequest = existingRequest.get();

            if (emprequest.getRequestType() == RequestType.Owner_Invite) {
                return ApiResponse.error(
                        "This salon has already invited you."
                );
            }

            return ApiResponse.error(
                    "You already have a pending request for this salon."
            );
        }

        EmploymentRequest employmentRequest=new EmploymentRequest(
        );
        employmentRequest.setSalon(salon);
        employmentRequest.setRequestType(RequestType.User_Request);
        employmentRequest.setReceiver(salon.getOwner());
        employmentRequest.setSender(user);
        employmentRequest.setStatus(EmploymentRequestStatus.Requested);
        employmentRequest.setCreatedAt(LocalDateTime.now());

        employmentRepo.save(employmentRequest);

        return ApiResponse.success("Request to this salon Successfully done" , employmentRequest);
    }

//    This  accept request method is bidirectional > so the user can accept the invite from the salon owner  ,
//    and the salon owwner  can accept the user request  .
    @Override
    @Transactional
    public ApiResponse<EmploymentRequest> acceptRequest(AcceptRequest acceptRequest, Long currentUserId) {
        Optional<EmploymentRequest> employmentreq=employmentRepo.findById(acceptRequest.requestId());
        if(employmentreq.isEmpty()){
            return ApiResponse.error("This Employment Request is not Found");
        }

        EmploymentRequest employmentRequest=employmentreq.get();

        Salon salon=employmentRequest.getSalon();
        User employee;

        if (employmentRequest.getStatus()
                != EmploymentRequestStatus.Requested) {

            return ApiResponse.error(
                    "This Employment Request is no longer pending"
            );
        }

        if(salon.getStatus()!=SalonStatus.Accepted){
            return ApiResponse.error("Salon is not active , you can not accept");
        }

        if (employmentRequest.getRequestType() == RequestType.User_Request) {

            if (salon.getOwner().getId()!=(currentUserId)) {
                return ApiResponse.error(
                        "Only the salon owner can accept this request"
                );
            }

        } else if (employmentRequest.getRequestType() == RequestType.Owner_Invite) {

            if (employmentRequest.getReceiver().getId()!=(currentUserId)) {
                return ApiResponse.error(
                        "Only the invited user can accept this invitation"
                );
            }
        }
        employee = employmentRequest.getReceiver();

        if(salon.getCurrentEmployeesNumber()>= salon.getMaxEmployeesNumber()){
            return ApiResponse.error("Salon is already full");
        }
        employmentRequest.setStatus(EmploymentRequestStatus.Accepted);
        employee.setRole(UserRole.Employee);
        salon.setCurrentEmployeesNumber(salon.getCurrentEmployeesNumber()+1);
        employee.setSalon(salon);
        employmentRepo.save(employmentRequest);
        salonRepo.save(salon);
        userRepo.save(employee);


        return ApiResponse.success("Employment Request Accepted",employmentRequest);
    }
//  This invitation method is from the salon owner to the user .
    @Override
    public ApiResponse<EmploymentRequest> sentInvitation(SentInvitation sentInvitation,Long salonId,Long senderId) {
     Optional<Salon> optionalsalon=salonRepo.findById(salonId);
     Optional<User> optionalSender=userRepo.findById(senderId);
     Optional<User> optionalReciever=userRepo.findById(sentInvitation.getUserId());

     if(optionalsalon.isEmpty()){
         return ApiResponse.error("Salon not found");
     }

     if(optionalSender.isEmpty()){
         return ApiResponse.error("Sender not found");
     }

     if(optionalReciever.isEmpty()){
         return ApiResponse.error("Receiver not found");
     }

     Salon salon=optionalsalon.get();
     User sender=optionalSender.get();
     User receiver=optionalReciever.get();

     if (salon.getStatus() != SalonStatus.Accepted) {
         return ApiResponse.error("Salon is not active.");
     }

        if (salon.getOwner().getId()!= sender.getId()) {
            return ApiResponse.error(
                    "You are not the owner of this salon"
            );
        }

     if(sender.getId()==receiver.getId()){
         return ApiResponse.error("You can not invite yourself");
     }

        Optional<EmploymentRequest> existingRequest =
                employmentRepo.findPendingRequest(
                        salon,
                        receiver,
                        EmploymentRequestStatus.Requested
                );

        if (existingRequest.isPresent()) {

            EmploymentRequest request = existingRequest.get();

            if (request.getRequestType() == RequestType.User_Request) {
                return ApiResponse.error(
                        "This user has already requested to join your salon."
                );
            }

            return ApiResponse.error(
                    "This user already has a pending invitation."
            );
        }

     if (salon.getCurrentEmployeesNumber() >= salon.getMaxEmployeesNumber()) {
         return ApiResponse.error("Salon is already full");
     }

     EmploymentRequest employmentRequest=new EmploymentRequest();
     employmentRequest.setSalon(salon);
     employmentRequest.setRequestType(RequestType.Owner_Invite);
     employmentRequest.setReceiver(receiver);
     employmentRequest.setSender(sender);
     employmentRequest.setStatus(EmploymentRequestStatus.Requested);
     employmentRequest.setCreatedAt(LocalDateTime.now());
     employmentRepo.save(employmentRequest);

        return ApiResponse.success("Invetation Sented Sucessfully",employmentRequest);
    }

    @Override
    @Transactional
    public ApiResponse<EmploymentRequest> rejectRequest(
            RejectRequest rejectRequest,
            Long currentUserId) {

        // 1. Find the request
        Optional<EmploymentRequest> optionalRequest =
                employmentRepo.findById(rejectRequest.getRequestId());

        if (optionalRequest.isEmpty()) {
            return ApiResponse.error(
                    "This Employment Request is not found"
            );
        }

        EmploymentRequest employmentRequest = optionalRequest.get();

        // 2. Request must still be pending
        if (employmentRequest.getStatus()
                != EmploymentRequestStatus.Requested) {

            return ApiResponse.error(
                    "This Employment Request is no longer pending"
            );
        }

        // 3. Get sender and receiver
        User sender = employmentRequest.getSender();
        User receiver = employmentRequest.getReceiver();

        // 4. Check whether the current user is involved
        boolean isSender =
                sender.getId()==(currentUserId);

        boolean isReceiver =
                receiver.getId()==(currentUserId);

        if (!isSender && !isReceiver) {

            return ApiResponse.error(
                    "You are not allowed to reject this request"
            );
        }

        // 5. Reject the request
        employmentRequest.setStatus(
                EmploymentRequestStatus.Rejected
        );

        // If your entity has this field:
         employmentRequest.setRejectionReason(
                 rejectRequest.getRejectionReason()
         );

        // If you have respondedAt:
        // employmentRequest.setRespondedAt(LocalDateTime.now());

        // 6. Save
        employmentRepo.save(employmentRequest);

        return ApiResponse.success(
                "Employment Request Rejected, "
                        + rejectRequest.getRejectionReason(),
                employmentRequest
        );
    }

    @Override
    public ApiResponse<EmploymentRequest> cancelRequest(CancelRequest cancelRequest, Long currentUserId) {
        Optional<EmploymentRequest> empreq=employmentRepo.findById(cancelRequest.requestId());
        if (empreq.isEmpty()) {
            return ApiResponse.error("This Employment Request is not found");
        }
        EmploymentRequest employmentRequest = empreq.get();
        if (employmentRequest.getStatus()!=EmploymentRequestStatus.Requested) {
            return ApiResponse.error("This Employment Request is no longer pending");
        }
        User sender = employmentRequest.getSender();
        if (sender.getId()!=currentUserId) {
            return ApiResponse.error("You are not the sender of this request");
        }
        employmentRequest.setStatus(EmploymentRequestStatus.Cancelled);
        employmentRepo.save(employmentRequest);
        return ApiResponse.success("The  requestis cancelled successfully",employmentRequest);
    }

}
