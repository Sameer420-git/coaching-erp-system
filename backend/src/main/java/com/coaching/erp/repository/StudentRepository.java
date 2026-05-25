package com.coaching.erp.repository;

import com.coaching.erp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
Student findByUserUsername(String username);
}