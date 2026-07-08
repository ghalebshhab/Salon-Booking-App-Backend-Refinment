package com.salon.backend.Entities.salons;

import com.salon.backend.Entities.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Salon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String salonPhoneNumber;

    private String ownerPhoneNumber;

    private String email;

    private String location;

    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    private User owner;

//    @OneToMany
//    @JoinColumn(name = "employee_id")
//    @ElementCollection(fetch = FetchType.LAZY)
//    private User employee;

    private Long currentEmployeesNumber;

    private Long maxEmployeesNumber;

    private LocalDate createdAt;

    private LocalTime openingTime;

    private LocalTime closingTime;

    private String profilePictureUrl;

    private SalonStatus status;
}
