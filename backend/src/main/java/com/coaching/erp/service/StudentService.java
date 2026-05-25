package com.coaching.erp.service;

import com.coaching.erp.dto.StudentDashboardDTO;
import com.coaching.erp.entity.*;
import com.coaching.erp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
private FeeRepository feeRepository;

@Autowired
private MarksRepository marksRepository;

@Autowired
private AttendanceRepository attendanceRepository;

public StudentDashboardDTO getDashboard(String username) {

    Student student = studentRepository.findByUserUsername(username);

if (student == null) {
    throw new RuntimeException("Student not found for user: " + username);
}

    StudentDashboardDTO dto = new StudentDashboardDTO();

    dto.setStudent(student);
    dto.setFees(feeRepository.findByStudentId(student.getId()));
    dto.setMarks(marksRepository.findByStudentId(student.getId()));
    dto.setAttendance(attendanceRepository.findByStudentId(student.getId()));

    return dto;
}
    @Autowired
    private UserRepository userRepository;

    // 🔥 MAIN METHOD (use this everywhere)
    public Student save(Student student) {

        // ❗ Check if user already exists
       User existingUser = userRepository
        .findByUsername(student.getRollNo())
        .orElse(null);

        if (existingUser != null) {
            throw new RuntimeException("Student already exists");
        }

        // 🔥 Create user login
        User user = new User();
        user.setUsername(student.getRollNo());
        user.setPassword("1234"); // default password (later we encrypt)
        user.setRole(Role.STUDENT);
        user.setBranchId(student.getBranchId());

        userRepository.save(user);

        // 🔥 Link user with student
        student.setUser(user);

        return studentRepository.save(student);
    }

    public Object getAllStudents() {
        return studentRepository.findAll();
    }
}