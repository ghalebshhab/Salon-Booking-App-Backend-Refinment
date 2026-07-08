package com.salon.backend.Repositories.Salon;

import com.salon.backend.Entities.salons.Salon;
import com.salon.backend.Entities.salons.SalonStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalonRepo extends JpaRepository<Salon,Long> {
Salon findByOwnerPhoneNumber(String ownerPhoneNumber);
Salon findByOwnerEmail(String ownerEmail);
Salon save(Salon salon);
List<Salon> findAll();
List<Salon> findByStatus(SalonStatus status);
Salon findById(long id);
Salon findByLocation(String location);
}
