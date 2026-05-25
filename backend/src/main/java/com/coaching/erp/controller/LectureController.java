package com.coaching.erp.controller;

import com.coaching.erp.entity.Lecture;
import com.coaching.erp.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lectures")
public class LectureController {

    @Autowired
    private LectureService lectureService;

    // 🔥 Schedule Lecture
    @PreAuthorize("hasRole('ADMIN') or hasRole('BRANCH_MANAGER')")
    @PostMapping("/{staffId}")
    public Lecture addLecture(@PathVariable Long staffId, @RequestBody Lecture lecture) {
        return lectureService.addLecture(staffId, lecture);
    }

    // 🔥 Complete Lecture
    @PreAuthorize("hasRole('ADMIN') or hasRole('BRANCH_MANAGER')")
    @PutMapping("/complete/{lectureId}")
    public Lecture completeLecture(@PathVariable Long lectureId) {
        return lectureService.completeLecture(lectureId);
    }

    // 🔥 Teacher view own lectures (future use)
    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/my")
    public String myLectures() {
        return "Teacher lectures dashboard";
    }
}