package com.salon.backend.Repositories.Salon;

import com.salon.backend.Entities.salons.Salon;
import com.salon.backend.Entities.salons.employment.EmploymentRequest;
import com.salon.backend.Entities.salons.employment.EmploymentRequestStatus;
import com.salon.backend.Entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmploymentRepo extends JpaRepository<EmploymentRequest, Long> {
    Boolean existsByEmploymentRequestId(Long employmentRequestId);
    Boolean existsBySenderAndReceiverAndStatus(User sender, User receiver, EmploymentRequestStatus status);
    List<EmploymentRequest> findAllBySalonAndStatus(Salon salon, EmploymentRequestStatus status);
    List<EmploymentRequest> findAllBySenderAndStatus(User sender, EmploymentRequestStatus status);

}
