package com.salon.backend.Repositories.Salon;

import com.salon.backend.Entities.salons.employment.EmploymentRequest;
import com.salon.backend.Entities.salons.employment.LeaveRequest;
import com.salon.backend.Entities.salons.employment.LeaveRequestStatus;
import com.salon.backend.Entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeaveRequestRepo
        extends JpaRepository<LeaveRequest, Long> {

    boolean existsByEmployeeAndStatus(
            User employee,
            LeaveRequestStatus status
    );

    Optional<LeaveRequest> findByEmployeeAndStatus(
            User employee,
            LeaveRequestStatus status
    );

    Optional<LeaveRequest> findByEmploymentRequest(
            EmploymentRequest employmentRequest
    );
}