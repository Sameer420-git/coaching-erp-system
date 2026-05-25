package com.coaching.erp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long staffId;

    private String name;
    private String subject;
    private String contact;

    @Enumerated(EnumType.STRING)
    private StaffRole role;

    // For TEACHER
    private Double salaryPerHour;
    private Double totalHoursWorked;

    // For STAFF
    private Double fixedSalary;

    private Double totalSalary;

    private Long branchId;
}