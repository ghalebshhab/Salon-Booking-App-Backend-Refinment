package com.salon.backend.Repositories.Salon;

import com.salon.backend.Entities.salons.Salon;
import com.salon.backend.Entities.salons.SalonStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalonRepo extends JpaRepository<Salon, Long> {

    Salon findByOwnerPhoneNumber(String ownerPhoneNumber);

    Salon findByOwnerEmail(String email);

    Salon findByEmail(String email);

    List<Salon> findByStatus(SalonStatus status);

    List<Salon> findByLocation(String location);

    boolean existsBySalonPhoneNumber(String salonPhoneNumber);

    boolean existsByOwnerPhoneNumber(String ownerPhoneNumber);

    boolean existsByEmail(String email);
}
