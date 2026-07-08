package com.salon.backend.Entities.users;


import com.salon.backend.Entities.salons.Salon;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String userName;

    private String password;

    private String phoneNumber;

    private UserRole role;

    private UserStatus status;

    @ManyToOne
    @JoinColumn(name = "salon_id")
    private Salon salon;


}
