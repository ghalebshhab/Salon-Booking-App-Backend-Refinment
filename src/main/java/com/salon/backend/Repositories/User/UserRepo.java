package com.salon.backend.Repositories.User;

import com.salon.backend.Entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
boolean existsByuserName(String username);
boolean existsByemail(String email);
boolean existsByphoneNumber(String phoneNumber);
User findByuserName(String username);
User findByemail(String email);

}
