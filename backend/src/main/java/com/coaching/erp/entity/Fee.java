package com.coaching.erp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feeId;

    private Double totalAmount;
    private Double paidAmount;
    private Double remainingAmount;

    private LocalDate paymentDate;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}