package com.salon.backend.Entities.salons.employment;


import com.salon.backend.Entities.salons.Salon;
import com.salon.backend.Entities.salons.hiringposts.HiringPost;
import com.salon.backend.Entities.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Salon salon;

    @ManyToOne(fetch = FetchType.LAZY)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hiring_post_id")
    private HiringPost hiringPost;

    @Enumerated(EnumType.STRING)
    private EmploymentRequestStatus status;

    @Enumerated(EnumType.STRING)
    private RequestType requestType;

    @Enumerated(EnumType.STRING)
    private EmploymentRequestSource requestSource;

    private LocalDateTime createdAt;

    private String rejectionReason;


}
