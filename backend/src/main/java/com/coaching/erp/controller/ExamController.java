package com.coaching.erp.controller;

import com.coaching.erp.entity.*;
import com.coaching.erp.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    private ExamService examService;

    // 🔥 Create Exam
    @PreAuthorize("hasRole('ADMIN') or hasRole('BRANCH_MANAGER')")
    @PostMapping
    public Exam createExam(@RequestBody Exam exam) {
        return examService.createExam(exam);
    }

    // 🔥 Add Marks
    @PreAuthorize("hasRole('ADMIN') or hasRole('BRANCH_MANAGER')")
    @PostMapping("/marks")
    public Marks addMarks(
            @RequestParam Long studentId,
            @RequestParam Long examId,
            @RequestParam Double marks
    ) {
        return examService.addMarks(studentId, examId, marks);
    }

    // 🔥 Student view marks
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/my")
    public String myMarks() {
        return "Student marks dashboard";
    }
}