package com.coaching.erp.controller;

import com.coaching.erp.dto.StudentDashboardDTO;
import com.coaching.erp.entity.Student;
import com.coaching.erp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // 🔥 ADMIN + BRANCH_MANAGER can add student
    @PreAuthorize("hasRole('ADMIN') or hasRole('BRANCH_MANAGER')")
    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.save(student);
    }

    // 🔥 ADMIN + BRANCH_MANAGER can view all students
    @PreAuthorize("hasRole('ADMIN') or hasRole('BRANCH_MANAGER')")
    @GetMapping
    public Object getAllStudents() {
        return studentService.getAllStudents();
    }

    // 🔥 STUDENT can view only their own data (basic version)
   @PreAuthorize("hasRole('STUDENT')")
@GetMapping("/me")
public StudentDashboardDTO getMyDashboard(Authentication authentication) {

    String username = authentication.getName();

    return studentService.getDashboard(username);
}
}