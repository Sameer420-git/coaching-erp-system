package com.coaching.erp.controller;

import com.coaching.erp.entity.Fee;
import com.coaching.erp.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fees")
public class FeeController {

    @Autowired
    private FeeService feeService;

    // 🔥 Add fee
    @PreAuthorize("hasRole('ADMIN') or hasRole('BRANCH_MANAGER')")
    @PostMapping
    public Fee addFee(@RequestBody Fee fee) {
        return feeService.save(fee);
    }

    // 🔥 Student view fees
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/my")
    public String myFees() {
        return "Student fee details";
    }
}