package com.coaching.erp.controller;

import com.coaching.erp.entity.Staff;
import com.coaching.erp.service.StaffService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    // 🔥 Add staff
    @PreAuthorize("hasRole('ADMIN') or hasRole('BRANCH_MANAGER')")
    @PostMapping
    public Staff addStaff(@RequestBody Staff staff) {
        return staffService.save(staff);
    }

    // 🔥 Staff dashboard
@PreAuthorize("hasRole('TEACHER') or hasRole('STAFF')")
@GetMapping("/me")
public Staff getMyDetails(Authentication authentication) {

    String username = authentication.getName();

    return staffService.getByUsername(username);
}
}