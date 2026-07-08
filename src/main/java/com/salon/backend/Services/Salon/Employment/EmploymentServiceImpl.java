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
        if(salon.getCurrentEmployeesNumber().equals( salon.getMaxEmployeesNumber())){
            return ApiResponse.error("Salon is already full");
        }
        EmploymentRequest employmentRequest=new EmploymentRequest(
        );
        employmentRequest.setSalon(salon);
        employmentRequest.setRequestType(RequestType.User_Request);
        employmentRequest.setReceiver(salon.getOwner());
        employmentRequest.setSender(user);
        employmentRequest.setStatus(EmploymentRequestStatus.Requested);
        employmentRequest.setCreatedAt(LocalDateTime.now());
        if(employmentRepo.existsBySenderAndReceiverAndStatus(user,salon.getOwner(),EmploymentRequestStatus.Requested)){
            return  ApiResponse.error("This Salon already requested");
        }
        employmentRepo.save(employmentRequest);

        return ApiResponse.success("Request to this salon done ." , employmentRequest);
    }

    @Override
    public ApiResponse<EmploymentRequest> acceptRequest(AcceptRequest acceptRequest) {
        Optional<EmploymentRequest> employ=employmentRepo.findById(acceptRequest.requestId());
        if(employ.isEmpty()){
            return ApiResponse.error("This Employment Request is not Found");
        }

        EmploymentRequest employmentRequest=employ.get();
        User sender=employmentRequest.getSender();
        Salon salon=employmentRequest.getSalon();
        if(sender.getRole()!= UserRole.USER){
            return ApiResponse.error("This user is not allowed to join this Salon");
        }
        if(salon.getCurrentEmployeesNumber().equals( salon.getMaxEmployeesNumber())){
            return ApiResponse.error("Salon is already full");
        }
        employmentRequest.setStatus(EmploymentRequestStatus.Accepted);
        sender.setRole(UserRole.Employee);
        salon.setCurrentEmployeesNumber(salon.getCurrentEmployeesNumber()+1);
        sender.setSalon(salon);
        employmentRepo.save(employmentRequest);
        salonRepo.save(salon);
        userRepo.save(sender);


        return ApiResponse.success("Employment Request Accepted",employmentRequest);
    }

    @Override
    public ApiResponse<EmploymentRequest> sentInvitation(SentInvitation sentInvitation,Long salonId) {
     Optional<Salon> optionalsalon=salonRepo.findById(salonId);
     if(optionalsalon.isEmpty()){
         return ApiResponse.error("Salon not found");
     }
     Salon salon=optionalsalon.get();
     User sender=salon.getOwner();
     Optional<User> optionalReciever=userRepo.findById(sentInvitation.getUserId());
     if(optionalReciever.isEmpty()){
         return ApiResponse.error("Receiver not found");
     }
     User receiver=optionalReciever.get();
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
    public ApiResponse<EmploymentRequest> rejectRequest(RejectRequest rejectRequest) {
        Optional<EmploymentRequest> employ=employmentRepo.findById(rejectRequest.requestId());
        if(employ.isEmpty()){
            return ApiResponse.error("This Employment Request is not Found");
        }

        EmploymentRequest employmentRequest=employ.get();
        employmentRequest.setStatus(EmploymentRequestStatus.Rejected);
        employmentRepo.save(employmentRequest);


        return ApiResponse.success("Employment Request Rejected , "+ rejectRequest.rejectionReason(),employmentRequest);
    }
}
