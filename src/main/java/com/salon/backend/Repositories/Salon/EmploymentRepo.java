package com.salon.backend.Repositories.Salon;

import com.salon.backend.Entities.salons.Salon;
import com.salon.backend.Entities.salons.employment.EmploymentRequest;
import com.salon.backend.Entities.salons.employment.EmploymentRequestStatus;
import com.salon.backend.Entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmploymentRepo extends JpaRepository<EmploymentRequest, Long> {
    boolean existsById(Long employmentRequestId);
    boolean existsBySenderAndReceiverAndStatus(User sender, User receiver, EmploymentRequestStatus status);
    boolean existsBySenderAndSalonAndStatus(User sender, Salon salon, EmploymentRequestStatus status);
    boolean existsBySalonAndUserAndStatus(User user, Salon salon, EmploymentRequestStatus status);
    List<EmploymentRequest> findAllBySalonAndStatus(Salon salon, EmploymentRequestStatus status);
    List<EmploymentRequest> findAllBySenderAndStatus(User sender, EmploymentRequestStatus status);
    @Query("""
    SELECT e
    FROM EmploymentRequest e
    WHERE e.salon = :salon
    AND e.status = :status
    AND (
        (e.requestType = RequestType.User_Request AND e.sender = :user)
        OR
        (e.requestType = RequestType.Owner_Invite AND e.receiver = :user)
    )
""")
    Optional<EmploymentRequest> findPendingRequest(
            @Param("salon") Salon salon,
            @Param("user") User user,
            @Param("status") EmploymentRequestStatus status
    );
}
