package com.coaching.erp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long examId;

    @Enumerated(EnumType.STRING)
    private ExamType examType;

    @Enumerated(EnumType.STRING)
    private MockType mockType; // only for MOCK

    @Enumerated(EnumType.STRING)
    private SubjectType subject;

    @Enumerated(EnumType.STRING)
    private TopicType topic;

    private String batch;
    private String standard; // 11th / 12th

    private Long branchId;

    private LocalDate examDate;

    private LocalTime startTime;
    private LocalTime endTime;

    private Double totalMarks;
}