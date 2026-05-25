package com.coaching.erp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String rollNo;
    private String studentContact;

    private String fatherName;
    private String fatherContact;

    private String motherName;
    private String motherContact;

    private String address;

    private String className;
    private String stream;
    private String courseType;

    private Double totalFees;
    private Double discount;
    private Double finalFees;

    private Long branchId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}