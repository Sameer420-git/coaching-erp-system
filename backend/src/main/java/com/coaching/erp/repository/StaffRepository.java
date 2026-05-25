package com.coaching.erp.repository;
import java.util.Optional;
import com.coaching.erp.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByName(String name);
}