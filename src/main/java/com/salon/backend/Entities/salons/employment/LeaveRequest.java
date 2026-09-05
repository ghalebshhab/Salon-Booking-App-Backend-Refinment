package com.salon.backend.Entities.salons.employment;


import com.salon.backend.Entities.salons.Salon;
import com.salon.backend.Entities.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User employee;

    @ManyToOne
    private Salon currentSalon;

    @ManyToOne
    private Salon targetSalon;

    @OneToOne
    private EmploymentRequest employmentRequest;

    @Enumerated(EnumType.STRING)
    private LeaveRequestStatus status;

    private String reason;

    private LocalDateTime createdAt;
}