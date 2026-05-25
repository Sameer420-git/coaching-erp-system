package com.coaching.erp.controller;

import com.coaching.erp.entity.Attendance;
import com.coaching.erp.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;


    @PreAuthorize("hasRole('ADMIN') or hasRole('BRANCH_MANAGER')")
    @PostMapping("/{studentId}")
    public Attendance markAttendance(
            @PathVariable Long studentId,
            @RequestBody Attendance attendance
    ) {
        return attendanceService.markAttendance(studentId, attendance);
    }

  
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/my")
    public String myAttendance() {
        return "Student attendance";
    }
}
