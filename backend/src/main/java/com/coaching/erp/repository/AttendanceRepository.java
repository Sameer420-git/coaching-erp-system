package com.coaching.erp.repository;
import java.util.List;

import com.coaching.erp.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
List<Attendance> findByStudentId(Long studentId);
}