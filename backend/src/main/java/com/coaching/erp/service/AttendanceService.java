package com.coaching.erp.service;

import com.coaching.erp.entity.*;
import com.coaching.erp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Attendance markAttendance(Long studentId, Attendance attendance) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        attendance.setStudent(student);

        // ✅ Set today's date automatically
        if (attendance.getDate() == null) {
            attendance.setDate(LocalDate.now());
        }

        return attendanceRepository.save(attendance);
    }
}