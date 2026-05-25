package com.coaching.erp.service;

import com.coaching.erp.entity.Staff;
import com.coaching.erp.entity.StaffRole;

import com.coaching.erp.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;
    public Staff save(Staff staff) {
    return staffRepository.save(staff);
}
public Staff getByUsername(String username) {
    return staffRepository.findByName(username)
            .orElseThrow(() -> new RuntimeException("Staff not found"));
}

   public Staff addStaff(Staff staff) {

    if (staff.getRole() == StaffRole.TEACHER) {

        if (staff.getSalaryPerHour() != null && staff.getTotalHoursWorked() != null) {
            staff.setTotalSalary(
                staff.getSalaryPerHour() * staff.getTotalHoursWorked()
            );
        }

    } else if (staff.getRole() == StaffRole.STAFF) {

        staff.setTotalSalary(staff.getFixedSalary());
    }

    return staffRepository.save(staff);
}
}