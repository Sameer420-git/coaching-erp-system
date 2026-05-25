package com.coaching.erp.service;

import com.coaching.erp.entity.*;
import com.coaching.erp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeeService {

    @Autowired
    private FeeRepository feeRepository;

    @Autowired
    private StudentRepository studentRepository;

    // 🔥 Generic save (with calculation)
    public Fee save(Fee fee) {

        if (fee.getTotalAmount() != null && fee.getPaidAmount() != null) {
            fee.setRemainingAmount(
                fee.getTotalAmount() - fee.getPaidAmount()
            );
        }

        return feeRepository.save(fee);
    }

    // 🔥 Save fee with student mapping
    public Fee addFee(Long studentId, Fee fee) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        fee.setStudent(student);

        return save(fee);   // ✅ reuse logic
    }
}