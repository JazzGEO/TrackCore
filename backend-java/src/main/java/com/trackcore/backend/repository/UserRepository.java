package com.trackcore.backend.repository;

import com.trackcore.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    List<User> findByDepartment(String department);
    
    List<User> findByActiveTrue();
    
    List<User> findByActiveFalse();
}